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
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(modelMapper);

        String internal_order_id = "test-internal-order-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String internal_order_file_id = "test-internal-order-file-id-1";
        String internal_order_file_name = "test-internal-order-file-name-1";

        InternalOrderDTO internalOrderDTO = InternalOrderDTO.builder().id(internal_order_id).version(version).internalOrderFiles(List.of(
                InternalOrderDTO.InternalOrderFileDTO.builder().id(internal_order_file_id).version(version).name(internal_order_file_name).build()
        )).build();

        InternalOrderEntity internalOrderEntity = modelMapper.map(internalOrderDTO, InternalOrderEntity.class);

        Assertions.assertNotNull(internalOrderEntity);
        Assertions.assertEquals(internal_order_id, internalOrderEntity.getId());
        Assertions.assertEquals(internal_order_id, internalOrderDTO.getId());

        Assertions.assertEquals(version, internalOrderEntity.getVersion());
        Assertions.assertEquals(version, internalOrderDTO.getVersion());

        Assertions.assertEquals(1, internalOrderEntity.getInternalOrderFileMetadataEntities().size());
        Assertions.assertEquals(1, internalOrderDTO.getInternalOrderFiles().size());

        internalOrderEntity.getInternalOrderFileMetadataEntities().forEach(internalOrderFileMetadataEntity -> {
            Assertions.assertNotNull(internalOrderFileMetadataEntity);
            Assertions.assertEquals(internal_order_file_id, internalOrderFileMetadataEntity.getId());
            Assertions.assertEquals(version, internalOrderFileMetadataEntity.getVersion());
            Assertions.assertEquals(internal_order_file_name, internalOrderFileMetadataEntity.getName());
        });
    }

    @Test
    @DisplayName("InternalOrderEntity -> InternalOrderDTO")
    public void convertInternalOrderEntity2InternalOrderDTOTest() {
        Assertions.assertNotNull(modelMapper);

        String internal_order_id = "test-internal-order-id-1";
        Timestamp version = new Timestamp(System.currentTimeMillis());
        String internal_order_file_id = "test-internal-order-file-id-1";
        String internal_order_file_name = "test-internal-order-file-name-1";

        InternalOrderEntity internalOrderEntity = InternalOrderEntity.builder().id(internal_order_id).version(version).internalOrderFileMetadataEntities(Set.of(
                InternalOrderFileMetadataEntity.builder().id(internal_order_file_id).version(version).name(internal_order_file_name).build()
        )).build();

        InternalOrderDTO internalOrderDTO = modelMapper.map(internalOrderEntity, InternalOrderDTO.class);

        Assertions.assertNotNull(internalOrderDTO);

        Assertions.assertEquals(internal_order_id, internalOrderDTO.getId());
        Assertions.assertEquals(internal_order_id, internalOrderEntity.getId());

        Assertions.assertEquals(version, internalOrderDTO.getVersion());
        Assertions.assertEquals(version, internalOrderEntity.getVersion());

        Assertions.assertEquals(1, internalOrderDTO.getInternalOrderFiles().size());
        Assertions.assertEquals(1, internalOrderEntity.getInternalOrderFileMetadataEntities().size());

        internalOrderDTO.getInternalOrderFiles().forEach(internalOrderFileDTO -> {
            Assertions.assertNotNull(internalOrderFileDTO);

            Assertions.assertEquals(internal_order_file_id, internalOrderFileDTO.getId());
            Assertions.assertEquals(version, internalOrderFileDTO.getVersion());
            Assertions.assertEquals(internal_order_file_name, internalOrderFileDTO.getName());
        });
    }

    @Test
    @DisplayName("PersonalComputerDTO -> PersonalComputerEntity")
    public void convertPersonalComputerDTO2PersonalComputerEntityTest() {
        Assertions.assertNotNull(modelMapper);

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

        Assertions.assertEquals(id, personalComputerEntity.getId());
        Assertions.assertEquals(id, personalComputerDTO.getId());
        Assertions.assertEquals(version, personalComputerEntity.getVersion());
        Assertions.assertEquals(version, personalComputerDTO.getVersion());
        Assertions.assertEquals(caseName, personalComputerEntity.getComputerCase());
        Assertions.assertEquals(caseName, personalComputerDTO.getComputerCase());
        Assertions.assertEquals(motherboard, personalComputerEntity.getMotherboard());
        Assertions.assertEquals(motherboard, personalComputerDTO.getMotherboard());
        Assertions.assertEquals(processor, personalComputerEntity.getProcessor());
        Assertions.assertEquals(processor, personalComputerDTO.getProcessor());
        Assertions.assertEquals(graphicsCard, personalComputerEntity.getGraphicsCard());
        Assertions.assertEquals(graphicsCard, personalComputerDTO.getGraphicsCard());
        Assertions.assertEquals(randomAccessMemory, personalComputerEntity.getRandomAccessMemory());
        Assertions.assertEquals(randomAccessMemory, personalComputerDTO.getRandomAccessMemory());
        Assertions.assertEquals(storageDevice, personalComputerEntity.getStorageDevice());
        Assertions.assertEquals(storageDevice, personalComputerDTO.getStorageDevice());
        Assertions.assertEquals(powerSupplyUnit, personalComputerEntity.getPowerSupplyUnit());
        Assertions.assertEquals(powerSupplyUnit, personalComputerDTO.getPowerSupplyUnit());
    }

    @Test
    @DisplayName("PersonalComputerEntity -> PersonalComputerDTO")
    public void convertPersonalComputerEntity2PersonalComputerDTOTest() {
        Assertions.assertNotNull(modelMapper);

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

        Assertions.assertNotNull(personalComputerDTO);
        Assertions.assertEquals(id, personalComputerDTO.getId());
        Assertions.assertEquals(id, personalComputerEntity.getId());
        Assertions.assertEquals(version, personalComputerDTO.getVersion());
        Assertions.assertEquals(version, personalComputerEntity.getVersion());
        Assertions.assertEquals(caseName, personalComputerDTO.getComputerCase());
        Assertions.assertEquals(caseName, personalComputerEntity.getComputerCase());
        Assertions.assertEquals(motherboard, personalComputerDTO.getMotherboard());
        Assertions.assertEquals(motherboard, personalComputerEntity.getMotherboard());
        Assertions.assertEquals(processor, personalComputerDTO.getProcessor());
        Assertions.assertEquals(processor, personalComputerEntity.getProcessor());
        Assertions.assertEquals(graphicsCard, personalComputerDTO.getGraphicsCard());
        Assertions.assertEquals(graphicsCard, personalComputerEntity.getGraphicsCard());
        Assertions.assertEquals(randomAccessMemory, personalComputerDTO.getRandomAccessMemory());
        Assertions.assertEquals(randomAccessMemory, personalComputerEntity.getRandomAccessMemory());
        Assertions.assertEquals(storageDevice, personalComputerDTO.getStorageDevice());
        Assertions.assertEquals(storageDevice, personalComputerEntity.getStorageDevice());
        Assertions.assertEquals(powerSupplyUnit, personalComputerDTO.getPowerSupplyUnit());
        Assertions.assertEquals(powerSupplyUnit, personalComputerEntity.getPowerSupplyUnit());
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

        Assertions.assertNotNull(internalOrderShortDTO);
        Assertions.assertEquals(internal_order_id, internalOrderShortDTO.getId());
        Assertions.assertEquals(version, internalOrderShortDTO.getVersion());
        Assertions.assertEquals("test surname, test name", internalOrderShortDTO.getClient());
        Assertions.assertEquals("test cpu, test gpu", internalOrderShortDTO.getPersonalComputer());
        Assertions.assertEquals(InternalOrderShortDTO.Status.OPEN, internalOrderShortDTO.getStatus());
        Assertions.assertEquals(dateOfReceiving, internalOrderShortDTO.getDateOfReceiving());
    }
}
