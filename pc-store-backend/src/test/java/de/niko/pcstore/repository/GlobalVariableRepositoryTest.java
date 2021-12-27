package de.niko.pcstore.repository;

import de.niko.pcstore.entity.GlobalVariableEntity;
import de.niko.pcstore.entity.InternalOrderEntity;
import java.sql.Timestamp;
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
    @DisplayName("Test findAll and findById")
    public void findAllTest() {
        List<GlobalVariableEntity> globalVariableEntities = globalVariableRepository.findAll();

        Assertions.assertThat(globalVariableEntities).isNotNull();
        Assertions.assertThat(globalVariableEntities.size()).isEqualTo(2);
        globalVariableEntities.forEach(globalVariableEntity -> {
            Assertions.assertThat(globalVariableEntity.getId()).isNotNull();
            Assertions.assertThat(globalVariableEntity.getVersion()).isNotNull();
        });

        GlobalVariableEntity secondInternalOrderEntity = globalVariableEntities.get(1);

        Optional<GlobalVariableEntity> internalOrderEntityOptional = globalVariableRepository.findById(secondInternalOrderEntity.getId());
        Assertions.assertThat(internalOrderEntityOptional.isPresent()).isTrue();
    }


    @BeforeEach
    public void initializeDatabase() {
        Assertions.assertThat(globalVariableRepository).isNotNull();

        GlobalVariableEntity globalVariableEntity1 = GlobalVariableEntity.builder().type("GVE-type-1").subtype("GVE-subtype-1").name("GVE-name-1").deletable(true).build();
        GlobalVariableEntity globalVariableEntity2 = GlobalVariableEntity.builder().type("GVE-type-2").subtype("GVE-subtype-2").name("GVE-name-2").deletable(false).build();
        GlobalVariableEntity globalVariableEntity3 = GlobalVariableEntity.builder().type("GVE-type-3").subtype("GVE-subtype-3").name("GVE-name-3").deletable(true).dateOfDeletion(new Timestamp(System.currentTimeMillis())).build();

        globalVariableRepository.saveAllAndFlush(List.of(globalVariableEntity1, globalVariableEntity2, globalVariableEntity3));
    }
}
