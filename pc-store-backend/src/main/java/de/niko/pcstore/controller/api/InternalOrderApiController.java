package de.niko.pcstore.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.dto.NewInternalOrderDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.entity.InternalOrderFilePayloadEntity;
import de.niko.pcstore.logging.InternalOrderApiLogMessages;
import de.niko.pcstore.repository.InternalOrderRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Slf4j
@Controller
public class InternalOrderApiController implements InternalOrderApi {
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final InternalOrderRepository internalOrderRepository;

    public InternalOrderApiController(InternalOrderRepository internalOrderRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.internalOrderRepository = internalOrderRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<Object> deleteGlobalVariable(String id) {
        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById(id);

        if (StringUtils.isEmpty(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            try {
                if (internalOrderEntityOptional.isPresent()) {
                    InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();

                    internalOrderEntity.setDateOfDeletion(new Timestamp(System.currentTimeMillis()));

                    internalOrderRepository.saveAndFlush(internalOrderEntity);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (org.springframework.dao.EmptyResultDataAccessException emptyResultDataAccessException) {
                emptyResultDataAccessException.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<List<InternalOrderShortDTO>> getAllInternalOrderList() {
        var internalOrderEntities = internalOrderRepository.findAll();

        var internalOrderDTOList = internalOrderEntities.stream().map(internalOrderEntity -> modelMapper.map(internalOrderEntity, InternalOrderShortDTO.class)).collect(Collectors.toUnmodifiableList());

        return new ResponseEntity<>(internalOrderDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InternalOrderDTO> getInternalOrder(String id) {
        log.info(InternalOrderApiLogMessages.GET_INTERNAL_ORDER_INPUT, id);
        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById(id);

        if (internalOrderEntityOptional.isPresent()) {
            InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();
            InternalOrderDTO internalOrderDTO = modelMapper.map(internalOrderEntity, InternalOrderDTO.class);

            log.info(InternalOrderApiLogMessages.GET_INTERNAL_ORDER_OUTPUT, internalOrderDTO);
            return new ResponseEntity<>(internalOrderDTO, HttpStatus.OK);
        } else {
            log.info(InternalOrderApiLogMessages.GET_INTERNAL_ORDER_OUTPUT_NOTFOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<InternalOrderDTO> addInternalOrder(NewInternalOrderDTO newInternalOrderDTO) {
        var internalOrderEntity = modelMapper.map(newInternalOrderDTO, InternalOrderEntity.class);
        internalOrderEntity.setStatusId("order-status-open");
        internalOrderEntity.setDateOfReceiving(LocalDate.now());

        var internalOrderSavedEntity = internalOrderRepository.saveAndFlush(internalOrderEntity);
        var internalOrderDTO = modelMapper.map(internalOrderSavedEntity, InternalOrderDTO.class);

        return new ResponseEntity<>(internalOrderDTO, HttpStatus.OK);
    }

    public static final String ADD_INTERNAL_ORDER_MULTIPART = "/internal-order-multipart";
    public static final String INTERNAL_ORDER_FILE_DOWNLOAD = "/download/internal-order-file/{metadata-id}";
    public static final String INTERNAL_ORDER_FILE_UPLOAD = "/upload/internal-order-file/{internal-order-id}";

    @SneakyThrows(value = {IOException.class})
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = INTERNAL_ORDER_FILE_UPLOAD,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> upload(@ApiParam(value = "internal order id", required = true) @PathVariable("internal-order-id") String internalOrderId, StandardMultipartHttpServletRequest standardMultipartHttpServletRequest) {
        String internalOrderFileAsJSON = standardMultipartHttpServletRequest.getParameter("internal-order-file");

        Optional<InternalOrderDTO.InternalOrderFileDTO> candidateFileDTOOptional = convertInternalOrderFileJSON2InternalOrderFileDTO(internalOrderFileAsJSON);

        if (candidateFileDTOOptional.isPresent()) {
            InternalOrderDTO.InternalOrderFileDTO internalOrderFileDTO = candidateFileDTOOptional.get();

            Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById(internalOrderId);

            if (internalOrderEntityOptional.isPresent()) {
                InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();

                MultipartFile multipartFile = standardMultipartHttpServletRequest.getFile("file");
                if (multipartFile != null) {
                    String name = internalOrderFileDTO.getName();
                    String note = internalOrderFileDTO.getNote();

                    String mimeType = multipartFile.getContentType();
                    byte[] payload = multipartFile.getBytes();

                    InternalOrderFileMetadataEntity internalOrderFileMetadataEntity = InternalOrderFileMetadataEntity.builder()
                            .name(name)
                            .note(note)
                            .internalOrderFilePayloadEntity(
                                    InternalOrderFilePayloadEntity.builder().mimeType(mimeType).payload(payload).build()
                            )
                            .build();

                    Set<InternalOrderFileMetadataEntity> internalOrderFileMetadataEntities = internalOrderEntity.getInternalOrderFileMetadataEntities();
                    if (internalOrderFileMetadataEntities == null) {
                        internalOrderFileMetadataEntities = new HashSet<>();
                    }
                    internalOrderFileMetadataEntities.add(internalOrderFileMetadataEntity);

                    internalOrderRepository.saveAndFlush(internalOrderEntity);
                }

                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    private Optional<InternalOrderDTO.InternalOrderFileDTO> convertInternalOrderFileJSON2InternalOrderFileDTO(String internalOrderFileAsJSON) {
        try {
            InternalOrderDTO.InternalOrderFileDTO internalOrderFileDTO = objectMapper.readValue(internalOrderFileAsJSON, InternalOrderDTO.InternalOrderFileDTO.class);

            return Optional.ofNullable(internalOrderFileDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = INTERNAL_ORDER_FILE_DOWNLOAD, method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@PathVariable("metadata-id") String metadataId) {
        ResponseEntity<byte[]> result;

        InternalOrderFileMetadataEntity optionalCandidateEntity = internalOrderRepository.getFileMetadata(metadataId);

        if (optionalCandidateEntity == null) {
            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            InternalOrderFilePayloadEntity personalComputerFilePayloadEntity = optionalCandidateEntity.getInternalOrderFilePayloadEntity();

            byte[] payload = personalComputerFilePayloadEntity.getPayload();
            String fileName = optionalCandidateEntity.getName();
            String mimeType = personalComputerFilePayloadEntity.getMimeType();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            responseHeaders.add(HttpHeaders.CONTENT_TYPE, mimeType);

            result = new ResponseEntity<>(payload, responseHeaders, HttpStatus.OK);
        }

        return result;
    }

    @Transactional
    @ApiOperation(hidden = true, value = "")
    @RequestMapping(value = ADD_INTERNAL_ORDER_MULTIPART,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> addInternalOrderMultipart(StandardMultipartHttpServletRequest standardMultipartHttpServletRequest) {
        String internalOrderAsJSON = standardMultipartHttpServletRequest.getParameter("internal-order");

        Optional<InternalOrderDTO> internalOrderDTOOptional = convertInternalOrderJSON2InternalOrderDTO(internalOrderAsJSON);

        if (internalOrderDTOOptional.isPresent()) {
            InternalOrderDTO internalOrderDTO = internalOrderDTOOptional.get();

            InternalOrderEntity internalOrderEntity = modelMapper.map(internalOrderDTO, InternalOrderEntity.class);
            internalOrderEntity.setStatusId("order-status-open");
            internalOrderEntity.setDateOfReceiving(LocalDate.now());

            Set<InternalOrderFileMetadataEntity> internalOrderFileMetadataEntities = internalOrderEntity.getInternalOrderFileMetadataEntities();
            if (!CollectionUtils.isEmpty(internalOrderFileMetadataEntities)) {
                internalOrderFileMetadataEntities.forEach(internalOrderFileMetadataEntity -> {
                    InternalOrderFilePayloadEntity internalOrderFilePayloadEntity = createInternalOrderFilePayloadEntity(internalOrderFileMetadataEntity, standardMultipartHttpServletRequest);

                    internalOrderFileMetadataEntity.setId(internalOrderFilePayloadEntity.getId());
                    internalOrderFileMetadataEntity.setInternalOrderFilePayloadEntity(internalOrderFilePayloadEntity);
                });
            }

            log.info("A PersonalComputerEntity {} will be inserted", internalOrderDTO);
            InternalOrderEntity internalOrderEntitySaved = internalOrderRepository.saveAndFlush(internalOrderEntity);

            InternalOrderDTO internalOrderDTOSaved = modelMapper.map(internalOrderEntitySaved, InternalOrderDTO.class);

            return new ResponseEntity<>(internalOrderDTOSaved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    private Optional<InternalOrderDTO> convertInternalOrderJSON2InternalOrderDTO(String internalOrderAsJSON) {
        try {
            InternalOrderDTO internalOrderDTO = objectMapper.readValue(internalOrderAsJSON, InternalOrderDTO.class);

            return Optional.ofNullable(internalOrderDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @SneakyThrows(value = {IOException.class})
    private InternalOrderFilePayloadEntity createInternalOrderFilePayloadEntity(InternalOrderFileMetadataEntity internalOrderFileMetadataEntity, StandardMultipartHttpServletRequest standardMultipartHttpServletRequest) {
        var internalOrderFileMetadataEntityId = internalOrderFileMetadataEntity.getId();

        var multipartFile = standardMultipartHttpServletRequest.getFile(internalOrderFileMetadataEntityId);

        var internalOrderFilePayloadEntity = new InternalOrderFilePayloadEntity();
        internalOrderFilePayloadEntity.setMimeType(multipartFile != null ? multipartFile.getContentType() : null);
        internalOrderFilePayloadEntity.setPayload(multipartFile != null ? multipartFile.getBytes() : null);

        internalOrderFileMetadataEntity.setInternalOrderFilePayloadEntity(internalOrderFilePayloadEntity);

        return internalOrderFilePayloadEntity;
    }
}
