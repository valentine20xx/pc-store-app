package de.niko.pcstore.configuration;

import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.entity.ClientDataEntity;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.entity.PersonalComputerEntity;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(MapperConfiguration.class)
@DisplayName("Test MapperConfiguration")
public class MapperConfigurationTest {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName("InternalOrderDTO -> InternalOrderEntity")
    public void convertInternalOrderDTO2InternalOrderEntityTest() {
        Assertions.assertThat(modelMapper).isNotNull();

        String internal_order_id = "test-internal-order-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String internal_order_file_id = "test-internal-order-file-id-1";
        String internal_order_file_name = "test-internal-order-file-name-1";

        InternalOrderDTO internalOrderDTO = InternalOrderDTO.builder().id(internal_order_id).version(version).internalOrderFiles(List.of(
                InternalOrderDTO.InternalOrderFileDTO.builder().id(internal_order_file_id).version(version).name(internal_order_file_name).build()
        )).build();

        InternalOrderEntity internalOrderEntity = modelMapper.map(internalOrderDTO, InternalOrderEntity.class);

        Assertions.assertThat(internalOrderEntity).isNotNull();
        Assertions.assertThat(internalOrderEntity.getId()).isEqualTo(internalOrderDTO.getId()).isEqualTo(internal_order_id);
        Assertions.assertThat(internalOrderEntity.getVersion()).isEqualTo(internalOrderDTO.getVersion()).isEqualTo(version);
        Assertions.assertThat(internalOrderEntity.getInternalOrderFileMetadataEntities().size()).isEqualTo(internalOrderDTO.getInternalOrderFiles().size()).isEqualTo(1);

        internalOrderEntity.getInternalOrderFileMetadataEntities().forEach(internalOrderFileMetadataEntity -> {
            Assertions.assertThat(internalOrderFileMetadataEntity).isNotNull();
            Assertions.assertThat(internalOrderFileMetadataEntity.getId()).isEqualTo(internal_order_file_id);
            Assertions.assertThat(internalOrderFileMetadataEntity.getVersion()).isEqualTo(version);
            Assertions.assertThat(internalOrderFileMetadataEntity.getName()).isEqualTo(internal_order_file_name);
        });
    }

    @Test
    @DisplayName("InternalOrderEntity -> InternalOrderDTO")
    public void convertInternalOrderEntity2InternalOrderDTOTest() {
        Assertions.assertThat(modelMapper).isNotNull();

        String internal_order_id = "test-internal-order-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String internal_order_file_id = "test-internal-order-file-id-1";
        String internal_order_file_name = "test-internal-order-file-name-1";

        InternalOrderEntity internalOrderEntity = InternalOrderEntity.builder().id(internal_order_id).version(version).internalOrderFileMetadataEntities(Set.of(
                InternalOrderFileMetadataEntity.builder().id(internal_order_file_id).version(version).name(internal_order_file_name).build()
        )).build();

        InternalOrderDTO internalOrderDTO = modelMapper.map(internalOrderEntity, InternalOrderDTO.class);

        Assertions.assertThat(internalOrderDTO).isNotNull();
        Assertions.assertThat(internalOrderDTO.getId()).isEqualTo(internalOrderEntity.getId()).isEqualTo(internal_order_id);
        Assertions.assertThat(internalOrderDTO.getVersion()).isEqualTo(internalOrderEntity.getVersion()).isEqualTo(version);
        Assertions.assertThat(internalOrderDTO.getInternalOrderFiles().size()).isEqualTo(internalOrderEntity.getInternalOrderFileMetadataEntities().size()).isEqualTo(1);

        internalOrderDTO.getInternalOrderFiles().forEach(internalOrderFileDTO -> {
            Assertions.assertThat(internalOrderFileDTO).isNotNull();
            Assertions.assertThat(internalOrderFileDTO.getId()).isEqualTo(internal_order_file_id);
            Assertions.assertThat(internalOrderFileDTO.getVersion()).isEqualTo(version);
            Assertions.assertThat(internalOrderFileDTO.getName()).isEqualTo(internal_order_file_name);
        });
    }

    @Test
    @DisplayName("PersonalComputerDTO -> PersonalComputerEntity")
    public void convertPersonalComputerDTO2PersonalComputerEntityTest() {
        Assertions.assertThat(modelMapper).isNotNull();

        String id = "test-personal-computer-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String caseName = "test-personal-computer-computerCase-1";
        String motherboard = "test-personal-computer-motherboard-1";
        String processor = "test-personal-computer-processor-1";
        String graphicsCard = "test-personal-computer-graphicsCard-1";
        String randomAccessMemory = "test-personal-computer-randomAccessMemory-1";
        String storageDevice = "test-personal-computer-storageDevice-1";
        String powerSupplyUnit = "test-personal-computer-powerSupplyUnit-1";

        InternalOrderDTO.PersonalComputerDTO personalComputerDTO = InternalOrderDTO.PersonalComputerDTO.builder().id(id).version(version).computerCase(caseName).motherboard(motherboard).processor(processor).graphicsCard(graphicsCard).randomAccessMemory(randomAccessMemory).storageDevice(storageDevice).powerSupplyUnit(powerSupplyUnit).build();

        PersonalComputerEntity personalComputerEntity = modelMapper.map(personalComputerDTO, PersonalComputerEntity.class);

        Assertions.assertThat(personalComputerEntity.getId()).isEqualTo(personalComputerDTO.getId()).isEqualTo(id);
        Assertions.assertThat(personalComputerEntity.getVersion()).isEqualTo(personalComputerDTO.getVersion()).isEqualTo(version);
        Assertions.assertThat(personalComputerEntity.getComputerCase()).isEqualTo(personalComputerDTO.getComputerCase()).isEqualTo(caseName);
        Assertions.assertThat(personalComputerEntity.getMotherboard()).isEqualTo(personalComputerDTO.getMotherboard()).isEqualTo(motherboard);
        Assertions.assertThat(personalComputerEntity.getProcessor()).isEqualTo(personalComputerDTO.getProcessor()).isEqualTo(processor);
        Assertions.assertThat(personalComputerEntity.getGraphicsCard()).isEqualTo(personalComputerDTO.getGraphicsCard()).isEqualTo(graphicsCard);
        Assertions.assertThat(personalComputerEntity.getRandomAccessMemory()).isEqualTo(personalComputerDTO.getRandomAccessMemory()).isEqualTo(randomAccessMemory);
        Assertions.assertThat(personalComputerEntity.getStorageDevice()).isEqualTo(personalComputerDTO.getStorageDevice()).isEqualTo(storageDevice);
        Assertions.assertThat(personalComputerEntity.getPowerSupplyUnit()).isEqualTo(personalComputerDTO.getPowerSupplyUnit()).isEqualTo(powerSupplyUnit);
    }

    @Test
    @DisplayName("PersonalComputerEntity -> PersonalComputerDTO")
    public void convertPersonalComputerEntity2PersonalComputerDTOTest() {
        Assertions.assertThat(modelMapper).isNotNull();

        String id = "test-personal-computer-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String caseName = "test-personal-computer-computerCase-1";
        String motherboard = "test-personal-computer-motherboard-1";
        String processor = "test-personal-computer-processor-1";
        String graphicsCard = "test-personal-computer-graphicsCard-1";
        String randomAccessMemory = "test-personal-computer-randomAccessMemory-1";
        String storageDevice = "test-personal-computer-storageDevice-1";
        String powerSupplyUnit = "test-personal-computer-powerSupplyUnit-1";

        PersonalComputerEntity personalComputerEntity = PersonalComputerEntity.builder().id(id).version(version).computerCase(caseName).motherboard(motherboard).processor(processor).graphicsCard(graphicsCard).randomAccessMemory(randomAccessMemory).storageDevice(storageDevice).powerSupplyUnit(powerSupplyUnit).build();

        InternalOrderDTO.PersonalComputerDTO personalComputerDTO = modelMapper.map(personalComputerEntity, InternalOrderDTO.PersonalComputerDTO.class);

        Assertions.assertThat(personalComputerDTO).isNotNull();
        Assertions.assertThat(personalComputerDTO.getId()).isEqualTo(personalComputerEntity.getId()).isEqualTo(id);
        Assertions.assertThat(personalComputerDTO.getVersion()).isEqualTo(personalComputerEntity.getVersion()).isEqualTo(version);
        Assertions.assertThat(personalComputerDTO.getComputerCase()).isEqualTo(personalComputerEntity.getComputerCase()).isEqualTo(caseName);
        Assertions.assertThat(personalComputerDTO.getMotherboard()).isEqualTo(personalComputerEntity.getMotherboard()).isEqualTo(motherboard);
        Assertions.assertThat(personalComputerDTO.getProcessor()).isEqualTo(personalComputerEntity.getProcessor()).isEqualTo(processor);
        Assertions.assertThat(personalComputerDTO.getGraphicsCard()).isEqualTo(personalComputerEntity.getGraphicsCard()).isEqualTo(graphicsCard);
        Assertions.assertThat(personalComputerDTO.getRandomAccessMemory()).isEqualTo(personalComputerEntity.getRandomAccessMemory()).isEqualTo(randomAccessMemory);
        Assertions.assertThat(personalComputerDTO.getStorageDevice()).isEqualTo(personalComputerEntity.getStorageDevice()).isEqualTo(storageDevice);
        Assertions.assertThat(personalComputerDTO.getPowerSupplyUnit()).isEqualTo(personalComputerEntity.getPowerSupplyUnit()).isEqualTo(powerSupplyUnit);
    }

    @Test
    @DisplayName("PersonalComputerEntity -> PersonalComputerShortDTO")
    public void convertPersonalComputerEntity2PersonalComputerShortDTOTest() {
        String internal_order_id = "test-internal-order-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());

        String processor = "test cpu";
        String graphicsCard = "test gpu";
        String name = "test name";
        String surname = "test surname";
        LocalDate dateOfReceiving = LocalDate.now();

        InternalOrderEntity internalOrderEntity = InternalOrderEntity.builder().id(internal_order_id).version(version)
                .personalComputer(PersonalComputerEntity.builder().processor(processor).graphicsCard(graphicsCard).build())
                .clientData(ClientDataEntity.builder().name(name).surname(surname).build())
                .dateOfReceiving(dateOfReceiving)
                .status(InternalOrderEntity.Status.OPEN)
                .build();

        InternalOrderShortDTO internalOrderShortDTO = modelMapper.map(internalOrderEntity, InternalOrderShortDTO.class);

        Assertions.assertThat(internalOrderShortDTO).isNotNull();
        Assertions.assertThat(internalOrderShortDTO.getId()).isNotNull();
        Assertions.assertThat(internalOrderShortDTO.getVersion()).isNotNull();
        Assertions.assertThat(internalOrderShortDTO.getClient()).isEqualTo("test surname, test name");
        Assertions.assertThat(internalOrderShortDTO.getPersonalComputer()).isEqualTo("test cpu, test gpu");
        Assertions.assertThat(internalOrderShortDTO.getStatus()).isEqualTo(InternalOrderShortDTO.Status.OPEN);
        Assertions.assertThat(internalOrderShortDTO.getDateOfReceiving()).isEqualTo(dateOfReceiving);
    }
}
