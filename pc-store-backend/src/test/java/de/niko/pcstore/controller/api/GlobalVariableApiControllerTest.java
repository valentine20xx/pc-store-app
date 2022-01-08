package de.niko.pcstore.controller.api;

import de.niko.pcstore.dto.GlobalVariableDTO;
import de.niko.pcstore.entity.GlobalVariableEntity;
import de.niko.pcstore.repository.GlobalVariableRepository;
import java.sql.Timestamp;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test GlobalVariableApiController")
public class GlobalVariableApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private GlobalVariableRepository globalVariableRepository;

    @Autowired
    private GlobalVariableApi globalVariableApi;

    @Test
    @DisplayName("Test getGlobalVariables")
//    @Transactional // a Session is needed (otherwise: failed to lazily initialize)
    @Sql("/test-data.sql")
    public void getGlobalVariablesTest() {
//        ResponseEntity<List<GlobalVariableDTO>> responseEntity = globalVariableApi.getGlobalVariables(List.of("GVE-type-1", "GVE-type-2"));
        ResponseEntity<List<GlobalVariableDTO>> responseEntity = globalVariableApi.getGlobalVariables(List.of("order-status", "salutations"));

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<GlobalVariableDTO> globalVariableDTOList = responseEntity.getBody();
        Assertions.assertThat(globalVariableDTOList).isNotNull();
        Assertions.assertThat(globalVariableDTOList.size()).isEqualTo(7);

        globalVariableDTOList.forEach(globalVariableDTO -> {
            String id = globalVariableDTO.getId();
            Timestamp version = globalVariableDTO.getVersion();
            String type = globalVariableDTO.getType();
            String subtype = globalVariableDTO.getSubtype();
            String name = globalVariableDTO.getName();
            Boolean deletable = globalVariableDTO.getDeletable();

            Assertions.assertThat(id).isNotNull();
            Assertions.assertThat(version).isNotNull();
            Assertions.assertThat(type).isNotNull();
            Assertions.assertThat(subtype).isNotNull();
            Assertions.assertThat(name).isNotNull();
            Assertions.assertThat(deletable).isNotNull();
        });
    }

//    @BeforeEach
//    private void initializeDatabase() {
//        Assertions.assertThat(port).isNotNull();
//        Assertions.assertThat(testRestTemplate).isNotNull();
//        Assertions.assertThat(globalVariableRepository).isNotNull();
//
//        GlobalVariableEntity globalVariableEntity1 = GlobalVariableEntity.builder().type("GVE-type-1").subtype("GVE-subtype-1").name("GVE-name-1").deletable(true).build();
//        GlobalVariableEntity globalVariableEntity1_2 = GlobalVariableEntity.builder().type("GVE-type-1").subtype("GVE-subtype-2").name("GVE-name-2").deletable(true).build();
//        GlobalVariableEntity globalVariableEntity2 = GlobalVariableEntity.builder().type("GVE-type-2").subtype("GVE-subtype-2").name("GVE-name-2").deletable(false).build();
//        GlobalVariableEntity globalVariableEntity3 = GlobalVariableEntity.builder().type("GVE-type-3").subtype("GVE-subtype-3").name("GVE-name-3").deletable(true).dateOfDeletion(new Timestamp(System.currentTimeMillis())).build();
//
//        globalVariableRepository.saveAllAndFlush(List.of(globalVariableEntity1, globalVariableEntity1_2, globalVariableEntity2, globalVariableEntity3));
//    }
}
