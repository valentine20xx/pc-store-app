package de.niko.pcstore.controller.api;

import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.dto.NewInternalOrderDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.logging.InternalOrderApiLogMessages;
import de.niko.pcstore.repository.InternalOrderRepository;
import io.swagger.v3.oas.annotations.Hidden;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Slf4j
@RestController
public class InternalOrderApiController implements InternalOrderApi {
    private final ModelMapper modelMapper;
    private final InternalOrderRepository internalOrderRepository;

    public InternalOrderApiController(InternalOrderRepository internalOrderRepository, //
                                      ModelMapper modelMapper) {
        this.internalOrderRepository = internalOrderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Object> deleteGlobalVariable(String id) {
        var internalOrderEntityOptional = internalOrderRepository.findById(id);

        if (StringUtils.isEmpty(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            try {
                if (internalOrderEntityOptional.isPresent()) {
                    var internalOrderEntity = internalOrderEntityOptional.get();

                    internalOrderEntity.setDateOfDeletion(new Timestamp(System.currentTimeMillis()));

                    internalOrderRepository.saveAndFlush(internalOrderEntity);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
                emptyResultDataAccessException.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<List<InternalOrderShortDTO>> getInternalOrderList(List<InternalOrderDTO.Status> statuses) {
        List<InternalOrderEntity> internalOrderEntities;

        if (statuses == null || statuses.size() == 0) {
            internalOrderEntities = internalOrderRepository.findAll();
        } else {
            internalOrderEntities = internalOrderRepository.findAllWithStatuses(statuses.stream().map(status -> InternalOrderEntity.Status.fromString(status.getStatus())).toList());
        }

        var internalOrderDTOList = internalOrderEntities.stream().map(internalOrderEntity -> modelMapper.map(internalOrderEntity, InternalOrderShortDTO.class)).toList();

        return new ResponseEntity<>(internalOrderDTOList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InternalOrderDTO> getInternalOrder(String id) {
        log.info(InternalOrderApiLogMessages.GET_INTERNAL_ORDER_INPUT, id);
        var internalOrderEntityOptional = internalOrderRepository.findById(id);

        if (internalOrderEntityOptional.isPresent()) {
            var internalOrderEntity = internalOrderEntityOptional.get();
            var internalOrderDTO = modelMapper.map(internalOrderEntity, InternalOrderDTO.class);

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
        internalOrderEntity.setStatus(InternalOrderEntity.Status.OPEN);
        internalOrderEntity.setDateOfReceiving(LocalDate.now());

        var internalOrderSavedEntity = internalOrderRepository.saveAndFlush(internalOrderEntity);
        var internalOrderDTO = modelMapper.map(internalOrderSavedEntity, InternalOrderDTO.class);

        return new ResponseEntity<>(internalOrderDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateInternalOrderStatus(String internalOrderId, InternalOrderDTO.Status status) {
        var internalOrderEntityOptional = internalOrderRepository.findById(internalOrderId);

        if (internalOrderEntityOptional.isPresent()) {
            var internalOrderEntity = internalOrderEntityOptional.get();

            if (internalOrderEntity.getStatus() == InternalOrderEntity.Status.fromString(status.getStatus())) {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

            internalOrderEntity.setStatus(InternalOrderEntity.Status.fromString(status.getStatus()));

            internalOrderRepository.saveAndFlush(internalOrderEntity);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<InternalOrderDTO.PersonalComputerDTO> getPersonalComputerConfigurationByOrderId(String orderId) {
        var internalOrderEntityOptional = internalOrderRepository.findById(orderId);

        if (internalOrderEntityOptional.isPresent()) {
            var internalOrderEntity = internalOrderEntityOptional.get();
            var personalComputerEntity = internalOrderEntity.getPersonalComputer();

            InternalOrderDTO.PersonalComputerDTO personalComputerDTO = modelMapper.map(personalComputerEntity, InternalOrderDTO.PersonalComputerDTO.class);

            return new ResponseEntity<>(personalComputerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}