package de.niko.pcstore.repository;

import de.niko.pcstore.entity.GlobalVariableEntity;
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
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test InternalOrderRepository")
public class InternalOrderRepositoryTest {
    @Autowired
    private InternalOrderRepository internalOrderRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Test findAll")
    @Sql("/test-internal-orders.sql")
    public void findAllTest() {
        List<InternalOrderEntity> internalOrderEntities = internalOrderRepository.findAll();

        Assertions.assertThat(internalOrderEntities).isNotNull();
        Assertions.assertThat(internalOrderEntities.size()).isEqualTo(1);
        internalOrderEntities.forEach(internalOrderEntity -> {
            Assertions.assertThat(internalOrderEntity.getId()).isNotNull();
            Assertions.assertThat(internalOrderEntity.getVersion()).isNotNull();
        });
    }

    @Test
    @DisplayName("Test findById")
    @Sql("/test-internal-orders.sql")
    public void findByIdTest() {
        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById("INTERNAL_ORDER_ID_1");
        Assertions.assertThat(internalOrderEntityOptional.isPresent()).isTrue();

        InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();
        Assertions.assertThat(internalOrderEntity.getId()).isEqualTo("INTERNAL_ORDER_ID_1");

        internalOrderEntityOptional = internalOrderRepository.findById("not-found");
        Assertions.assertThat(internalOrderEntityOptional.isEmpty()).isTrue();
    }
}
