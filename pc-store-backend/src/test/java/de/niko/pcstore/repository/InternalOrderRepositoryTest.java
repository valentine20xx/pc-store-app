package de.niko.pcstore.repository;

import de.niko.pcstore.entity.InternalOrderEntity;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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

        Assertions.assertThat(internalOrderEntities).isNotNull();
        Assertions.assertThat(internalOrderEntities.size()).isEqualTo(1);
        internalOrderEntities.forEach(internalOrderEntity -> {
            Assertions.assertThat(internalOrderEntity.getId()).isNotNull();
            Assertions.assertThat(internalOrderEntity.getVersion()).isNotNull();
        });
    }

    @Test
    @DisplayName("Test findById")
    @Sql("/test-data.sql")
    public void findByIdTest() {
        Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById("INTERNAL_ORDER_ID_1");
        Assertions.assertThat(internalOrderEntityOptional.isPresent()).isTrue();

        InternalOrderEntity internalOrderEntity = internalOrderEntityOptional.get();
        Assertions.assertThat(internalOrderEntity.getId()).isEqualTo("INTERNAL_ORDER_ID_1");

        internalOrderEntityOptional = internalOrderRepository.findById("not-found");
        Assertions.assertThat(internalOrderEntityOptional.isEmpty()).isTrue();
    }

    @BeforeEach
    private void initializeDatabase() {
        Assertions.assertThat(internalOrderRepository).isNotNull();
    }
}
