package de.niko.pcstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.entity.ClientDataEntity;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.entity.PersonalComputerEntity;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@Slf4j
@Configuration
public class MapperConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new ConvertPersonalComputerEntity2PersonalComputerDTO());
        modelMapper.addMappings(new ConvertPersonalComputerDTO2PersonalComputerEntity());

        modelMapper.addConverter(new ConvertInternalOrderEntity2InternalOrderShortDTO());

        return modelMapper;
    }

    static class ConvertPersonalComputerEntity2PersonalComputerDTO extends PropertyMap<InternalOrderEntity, InternalOrderDTO> {
        @Override
        protected void configure() {
            Converter<Set<InternalOrderFileMetadataEntity>, List<InternalOrderDTO.InternalOrderFileDTO>> converter = context -> {
                Set<InternalOrderFileMetadataEntity> source = context.getSource();

                return CollectionUtils.isEmpty(source) ? Collections.emptyList() : source.stream().map(personalComputerFileMetadataEntity -> InternalOrderDTO.InternalOrderFileDTO.builder().id(personalComputerFileMetadataEntity.getId()).version(personalComputerFileMetadataEntity.getVersion()).name(personalComputerFileMetadataEntity.getName()).note(personalComputerFileMetadataEntity.getNote()).build()).collect(Collectors.toList());
            };

            using(converter).map(source.getInternalOrderFileMetadataEntities(), destination.getInternalOrderFiles());
        }
    }

    static class ConvertPersonalComputerDTO2PersonalComputerEntity extends PropertyMap<InternalOrderDTO, InternalOrderEntity> {
        @Override
        protected void configure() {
            Converter<List<InternalOrderDTO.InternalOrderFileDTO>, Set<InternalOrderFileMetadataEntity>> converter = context -> {
                List<InternalOrderDTO.InternalOrderFileDTO> source = context.getSource();

                return CollectionUtils.isEmpty(source) ? Collections.emptySet() : source.stream().map(personalComputerFileDTO -> InternalOrderFileMetadataEntity.builder().id(personalComputerFileDTO.getId()).version(personalComputerFileDTO.getVersion()).name(personalComputerFileDTO.getName()).note(personalComputerFileDTO.getNote()).build()).collect(Collectors.toSet());
            };

            using(converter).map(source.getInternalOrderFiles(), destination.getInternalOrderFileMetadataEntities());
        }
    }

    static class ConvertInternalOrderEntity2InternalOrderShortDTO extends AbstractConverter<InternalOrderEntity, InternalOrderShortDTO> {
        @Override
        protected InternalOrderShortDTO convert(InternalOrderEntity internalOrderEntity) {
            PersonalComputerEntity personalComputer = internalOrderEntity.getPersonalComputer();

            String processor = personalComputer.getProcessor();
            String graphicsCard = personalComputer.getGraphicsCard();

            ClientDataEntity clientDataEntity = internalOrderEntity.getClientData();

            String name = clientDataEntity.getName();
            String surname = clientDataEntity.getSurname();

            String id = internalOrderEntity.getId();
            Timestamp version = internalOrderEntity.getVersion();

            String statusId = internalOrderEntity.getStatusId();
            LocalDate dateOfReceiving = internalOrderEntity.getDateOfReceiving();

            return InternalOrderShortDTO.builder()
                    .id(id)
                    .version(version)
                    .client(surname + ", " + name)
                    .personalComputer(processor + ", " + graphicsCard)
                    .dateOfReceiving(dateOfReceiving)
                    .statusId(statusId)
                    .build();
        }
    }
}
