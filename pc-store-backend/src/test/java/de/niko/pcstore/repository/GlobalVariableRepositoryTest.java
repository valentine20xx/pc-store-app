package de.niko.pcstore.repository;

import de.niko.pcstore.entity.GlobalVariableEntity;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test GlobalVariableRepository")
public class GlobalVariableRepositoryTest {
    @Autowired
    private GlobalVariableRepository globalVariableRepository;

    @Test
    @DisplayName("Test findAll")
    public void findAllTest() {
        List<GlobalVariableEntity> globalVariableEntities = globalVariableRepository.findAll();

        Assertions.assertThat(globalVariableEntities).isNotNull();
        Assertions.assertThat(globalVariableEntities.size()).isEqualTo(6);
        globalVariableEntities.forEach(globalVariableEntity -> {
            Assertions.assertThat(globalVariableEntity.getId()).isNotNull();
            Assertions.assertThat(globalVariableEntity.getVersion()).isNotNull();
        });
    }

    @Test
    @DisplayName("Test findById")
    public void findByIdTest() {
        Optional<GlobalVariableEntity> globalVariableEntityOptional = globalVariableRepository.findById("order-status-open");
        Assertions.assertThat(globalVariableEntityOptional.isPresent()).isTrue();

        GlobalVariableEntity globalVariableEntity = globalVariableEntityOptional.get();
        Assertions.assertThat(globalVariableEntity.getId()).isEqualTo("order-status-open");

        globalVariableEntityOptional = globalVariableRepository.findById("not-found");
        Assertions.assertThat(globalVariableEntityOptional.isEmpty()).isTrue();
    }

    @BeforeEach
    private void initializeDatabase() {
        Assertions.assertThat(globalVariableRepository).isNotNull();
    }
}
