package de.niko.pcstore.controller.api;


import de.niko.pcstore.dto.ErrorDTO;
import de.niko.pcstore.dto.GlobalVariableDTO;
import de.niko.pcstore.entity.GlobalVariableEntity;
import de.niko.pcstore.logging.GlobalVariableApiLogMessages;
import de.niko.pcstore.repository.GlobalVariableRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

@Slf4j
@Controller
public class GlobalVariableApiController implements GlobalVariableApi {
    private final GlobalVariableRepository globalVariableRepository;
    private final ModelMapper modelMapper;

    public GlobalVariableApiController(GlobalVariableRepository globalVariableRepository, ModelMapper modelMapper) {
        this.globalVariableRepository = globalVariableRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<GlobalVariableDTO> addGlobalVariable(GlobalVariableDTO globalVariableDTO) {
        log.debug(GlobalVariableApiLogMessages.ADD_GLOBAL_VARIABLE_INPUT, globalVariableDTO);

        var newConstantEntity = modelMapper.map(globalVariableDTO, GlobalVariableEntity.class);

        newConstantEntity.setVersion(null);
        newConstantEntity.setDeletable(true);

        var dbConstantEntity = globalVariableRepository.saveAndFlush(newConstantEntity);
        var dbConstantDTO = modelMapper.map(dbConstantEntity, GlobalVariableDTO.class);

        log.debug(GlobalVariableApiLogMessages.ADD_GLOBAL_VARIABLE_OUTPUT, dbConstantDTO);
        return new ResponseEntity<>(dbConstantDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GlobalVariableDTO>> getGlobalVariables(List<String> types) {
        log.debug(GlobalVariableApiLogMessages.GET_GLOBAL_VARIABLES_INPUT, types);

        var typeListIsEmpty = CollectionUtils.isEmpty(types);
        var constantEntities = typeListIsEmpty ? globalVariableRepository.findAll() : globalVariableRepository.getAllByTypes(types);
        var constants = constantEntities.stream().map(constantEntity -> modelMapper.map(constantEntity, GlobalVariableDTO.class)).collect(Collectors.toList());

        log.debug(GlobalVariableApiLogMessages.GET_GLOBAL_VARIABLES_OUTPUT, constants);
        return new ResponseEntity<>(constants, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deleteGlobalVariable(String id) {
        log.debug(GlobalVariableApiLogMessages.DELETE_GLOBAL_VARIABLE_INPUT, id);

        try {
            var globalVariableEntity = globalVariableRepository.getById(id);

            if (globalVariableEntity.getDeletable()) {
                globalVariableRepository.delete(globalVariableEntity);

                log.debug(GlobalVariableApiLogMessages.DELETE_GLOBAL_VARIABLE_OUTPUT_OK);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                log.debug(GlobalVariableApiLogMessages.DELETE_GLOBAL_VARIABLE_OUTPUT_CONFLICT);
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (EntityNotFoundException enfe) {
            log.debug(GlobalVariableApiLogMessages.DELETE_GLOBAL_VARIABLE_OUTPUT_NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updateGlobalVariable(GlobalVariableDTO globalVariableDTO) {
        log.debug(GlobalVariableApiLogMessages.UPDATE_GLOBAL_VARIABLE_INPUT, globalVariableDTO);

        String id = globalVariableDTO.getId();
        Timestamp version = globalVariableDTO.getVersion();

        if (StringUtils.isEmpty(id) || version == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var constantEntity = modelMapper.map(globalVariableDTO, GlobalVariableEntity.class);

        constantEntity.setDeletable(true);

        try {
            var savedConstantEntity = globalVariableRepository.saveAndFlush(constantEntity);
            var savedConstantDTO = modelMapper.map(savedConstantEntity, GlobalVariableDTO.class);

            log.debug(GlobalVariableApiLogMessages.UPDATE_GLOBAL_VARIABLE_OUTPUT, savedConstantDTO);
            return new ResponseEntity<>(savedConstantDTO, HttpStatus.OK);
        } catch (ObjectOptimisticLockingFailureException objectOptimisticLockingFailureException) {
            objectOptimisticLockingFailureException.printStackTrace();
            // TODO: Fix the line
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            var errorDTO = ErrorDTO.builder().code(1).message("Optimistic locking failure").build();

            log.debug(GlobalVariableApiLogMessages.UPDATE_GLOBAL_VARIABLE_ERROR, errorDTO);
            return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @Override
//    public ResponseEntity<ConstantDTO> getConstant(String id) {
//        log.debug(ConstantApiLogMessages.GET_CONSTANT_INPUT, id);
//
//        var constantEntityOptional = constantRepository.findById(id);
//        if (constantEntityOptional.isPresent()) {
//            var constantEntity = constantEntityOptional.get();
//
//            var constant = modelMapper.map(constantEntity, ConstantDTO.class);
//
//            log.debug(ConstantApiLogMessages.GET_CONSTANT_OUTPUT, constant);
//            return new ResponseEntity<>(constant, HttpStatus.OK);
//        } else {
//            log.debug(ConstantApiLogMessages.GET_CONSTANT_OUTPUT_NOTFOUND);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
