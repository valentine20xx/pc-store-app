package de.niko.pcstore.controller;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RedirectControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Test redirect2swaggeruiindexhtml")
    public void redirect2swaggeruiindexhtmlTest() {
        String BASE_URI = "http://localhost:" + port;

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(BASE_URI + RedirectController.CONTEXT_TO_REDIRECT_1, String.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @BeforeEach
    private void initializeDatabase() {
        Assertions.assertThat(port).isNotNull();
        Assertions.assertThat(testRestTemplate).isNotNull();
    }
}
