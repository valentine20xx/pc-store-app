package de.niko.pcstore.repository;

import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.entity.PersonalComputerEntity;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test InternalOrderRepository")
public class InternalOrderRepositoryTest {
    @Autowired
    private InternalOrderRepository internalOrderRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test findAll and findById")
    public void findAllTest() {
        List<InternalOrderEntity> internalOrderEntities = internalOrderRepository.findAll();

        Assertions.assertThat(internalOrderEntities).isNotNull();
        Assertions.assertThat(internalOrderEntities.size()).isEqualTo(2);
        internalOrderEntities.forEach(internalOrderEntity -> {
            Assertions.assertThat(internalOrderEntity.getId()).isNotNull();
            Assertions.assertThat(internalOrderEntity.getVersion()).isNotNull();
        });

        InternalOrderEntity secondInternalOrderEntity = internalOrderEntities.get(1);

        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById(secondInternalOrderEntity.getId());
        Assertions.assertThat(internalOrderEntityOptional.isPresent()).isTrue();
    }

//    @Test
//    @DisplayName("Test save")
//    public void saveTest() {
//        InternalOrderEntity internalOrderEntity = InternalOrderEntity.builder().internalOrderFileMetadataEntities(Set.of(
//                InternalOrderFileMetadataEntity.builder().name("IOFME-name").build()
//        )).build();
//
//        InternalOrderEntity internalOrderSavedEntity = internalOrderRepository.saveAndFlush(internalOrderEntity);
//
//        Query query = testEntityManager.getEntityManager().createNativeQuery("SELECT * FROM INTERNAL_ORDER JOIN INTERNAL_ORDER_FILE_METADATA IOFM on INTERNAL_ORDER.ID = IOFM.INTERNAL_ORDER_ID", InternalOrderEntity.class);
//        List<Object> resultList = query.getResultList();
//        Assertions.assertThat(resultList).isNotNull();
//
//
////        PersonalComputerEntity personalComputerEntity = personalComputerRepository.saveAll();
//    }

    @BeforeEach
    public void initializeDatabase() {
        Assertions.assertThat(internalOrderRepository).isNotNull();
        Assertions.assertThat(testEntityManager).isNotNull();

        InternalOrderEntity internalOrderEntity1 = InternalOrderEntity.builder().build();
        InternalOrderEntity internalOrderEntity2 = InternalOrderEntity.builder().personalComputer(
                PersonalComputerEntity.builder().processor("PCE-processor-1").build()
        ).build();
        InternalOrderEntity internalOrderEntity3 = InternalOrderEntity.builder().dateOfDeletion(new Timestamp(System.currentTimeMillis())).build();

        internalOrderRepository.saveAllAndFlush(List.of(internalOrderEntity1, internalOrderEntity2, internalOrderEntity3));
    }
}
