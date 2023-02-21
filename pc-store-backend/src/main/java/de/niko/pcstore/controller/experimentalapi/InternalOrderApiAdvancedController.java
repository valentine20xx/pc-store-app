package de.niko.pcstore.controller.experimentalapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.NewInternalOrderFileDTO;
import de.niko.pcstore.dto.NewInternalOrderMPDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.entity.InternalOrderFilePayloadEntity;
import de.niko.pcstore.repository.InternalOrderRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Slf4j
@RestController
public class InternalOrderApiAdvancedController {
    public static final String ADD_INTERNAL_ORDER_MULTIPART = "/internal-order-multipart";
    public static final String INTERNAL_ORDER_FILE_DOWNLOAD = "/download/internal-order-file/{metadata-id}";
    public static final String INTERNAL_ORDER_FILE_UPLOAD = "/upload/internal-order-file/{internal-order-id}";

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final InternalOrderRepository internalOrderRepository;


    public InternalOrderApiAdvancedController(InternalOrderRepository internalOrderRepository, //
                                              ModelMapper modelMapper, //
                                              ObjectMapper objectMapper) {
        this.internalOrderRepository = internalOrderRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows(value = {IOException.class})
    @RequestMapping(value = INTERNAL_ORDER_FILE_UPLOAD,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> upload(
//            @ApiParam(value = "internal order id", required = true)
            @PathVariable("internal-order-id") String internalOrderId,
//            File newFile,
            StandardMultipartHttpServletRequest standardMultipartHttpServletRequest) {
        String internalOrderFileAsJSON = standardMultipartHttpServletRequest.getParameter("internal-order-file");

        Optional<NewInternalOrderFileDTO> candidateFileDTOOptional = convertInternalOrderFileJSON2InternalOrderFileDTO(internalOrderFileAsJSON);

        if (candidateFileDTOOptional.isPresent()) {
            NewInternalOrderFileDTO internalOrderFileDTO = candidateFileDTOOptional.get();

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

    private Optional<NewInternalOrderFileDTO> convertInternalOrderFileJSON2InternalOrderFileDTO(String internalOrderFileAsJSON) {
        try {
            NewInternalOrderFileDTO internalOrderFileDTO = objectMapper.readValue(internalOrderFileAsJSON, NewInternalOrderFileDTO.class);

            return Optional.ofNullable(internalOrderFileDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

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
    @RequestMapping(value = ADD_INTERNAL_ORDER_MULTIPART,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            method = RequestMethod.POST)
    public ResponseEntity<Object> addInternalOrderMultipart(StandardMultipartHttpServletRequest standardMultipartHttpServletRequest) {
        String internalOrderAsJSON = standardMultipartHttpServletRequest.getParameter("internal-order");

        Optional<NewInternalOrderMPDTO> internalOrderDTOOptional = convertInternalOrderJSON2InternalOrderDTO(internalOrderAsJSON);

        if (internalOrderDTOOptional.isPresent()) {
            NewInternalOrderMPDTO internalOrderDTO = internalOrderDTOOptional.get();

            InternalOrderEntity internalOrderEntity = modelMapper.map(internalOrderDTO, InternalOrderEntity.class);
            internalOrderEntity.setStatus(InternalOrderEntity.Status.OPEN);
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

    private Optional<NewInternalOrderMPDTO> convertInternalOrderJSON2InternalOrderDTO(String internalOrderAsJSON) {
        try {
            NewInternalOrderMPDTO internalOrderDTO = objectMapper.readValue(internalOrderAsJSON, NewInternalOrderMPDTO.class);

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

        var contentType = multipartFile != null ? multipartFile.getContentType() : null;
        var bytes = multipartFile != null ? multipartFile.getBytes() : null;

        internalOrderFilePayloadEntity.setMimeType(contentType);
        internalOrderFilePayloadEntity.setPayload(bytes);

        internalOrderFileMetadataEntity.setInternalOrderFilePayloadEntity(internalOrderFilePayloadEntity);

        return internalOrderFilePayloadEntity;
    }
}
