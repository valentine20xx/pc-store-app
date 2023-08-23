package de.niko.pcstore.repository;

import de.niko.pcstore.entity.ClientDataEntity;
import de.niko.pcstore.entity.InternalOrderEntity;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test InternalOrderRepository")
public class InternalOrderRepositoryTest {
    @Autowired
    private InternalOrderRepository internalOrderRepository;

    @Test
    @DisplayName("Test findAll")
    @Sql("/test-data.sql")
    public void findAllTest() {
        List<InternalOrderEntity> internalOrderEntities = internalOrderRepository.findAll();

        Assertions.assertNotNull(internalOrderEntities);
        Assertions.assertEquals(3, internalOrderEntities.size());
        internalOrderEntities.forEach(internalOrderEntity -> {
            Assertions.assertNotNull(internalOrderEntity.getId());
            Assertions.assertNotNull(internalOrderEntity.getVersion());
        });
    }

    @Test
    @DisplayName("Test findById")
    @Sql("/test-data.sql")
    public void findByIdTest() {
        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById("INTERNAL_ORDER_ID_1");
        Assertions.assertTrue(internalOrderEntityOptional.isPresent());

        InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();
        Assertions.assertEquals("INTERNAL_ORDER_ID_1", internalOrderEntity.getId());

        internalOrderEntityOptional = internalOrderRepository.findById("not-found");
        Assertions.assertTrue(internalOrderEntityOptional.isEmpty());
    }

    @Test
    @DisplayName("Test findAllWithStatuses")
    @Sql("/test-data.sql")
    public void findAllWithStatusesTest() {
        List<InternalOrderEntity> internalOrderEntities = internalOrderRepository.findAllWithStatuses(Arrays.asList(InternalOrderEntity.Status.OPEN, InternalOrderEntity.Status.CHECKED));

        Assertions.assertNotNull(internalOrderEntities);
        Assertions.assertEquals(2, internalOrderEntities.size());
    }

    @Test
    @DisplayName("Test getFileMetadata")
    public void getFileMetadataTest() {

    }

    @Test
    @DisplayName("Test saveAndFlush")
    public void saveAndFlushTest() {
        InternalOrderEntity internalOrderEntity = new InternalOrderEntity();
        internalOrderEntity.setStatus(InternalOrderEntity.Status.OPEN);

        ClientDataEntity clientDataEntity = new ClientDataEntity();
        clientDataEntity.setName("TestName");
        clientDataEntity.setSurname("TestSurname");

        internalOrderEntity.setClientData(clientDataEntity);

        InternalOrderEntity internalOrderEntitySaved = internalOrderRepository.saveAndFlush(internalOrderEntity);

        Assertions.assertNotNull(internalOrderEntitySaved.getId());
        Assertions.assertEquals(36, internalOrderEntitySaved.getId().length());

        Pattern pattern = Pattern.compile("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(internalOrderEntitySaved.getId());
        Assertions.assertTrue(matcher.find());

        Assertions.assertNotNull(internalOrderEntitySaved.getVersion());
    }


    @BeforeEach
    public void initializeDatabase() {
        Assertions.assertNotNull(internalOrderRepository);
    }
}
