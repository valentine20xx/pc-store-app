package de.niko.pcstore.controller.api;

import de.niko.pcstore.dto.ClientDataDTO;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderFileDTO;
import de.niko.pcstore.dto.PersonalComputerDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.entity.InternalOrderFileMetadataEntity;
import de.niko.pcstore.repository.InternalOrderRepository;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureTestEntityManager
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test PersonalComputerApiController")
public class InternalOrderApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private InternalOrderRepository internalOrderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private InternalOrderApi internalOrderApi;

    @Test
    @DisplayName("Test upload")
    @Transactional
    public void uploadTest() {
        String BASE_URI = "http://localhost:" + port;

        String idForUpload;
        {
            InternalOrderDTO internalOrderDTO = InternalOrderDTO.builder().clientData(
                    ClientDataDTO.builder().name("CD-name-1").surname("CD-name-2").build()
            ).personalComputer(
                    PersonalComputerDTO.builder().processor("PC-processor-1").graphicsCard("PC-graphicsCard-1").build()
            ).build();
            HttpEntity<InternalOrderDTO> requestEntity = new HttpEntity<>(internalOrderDTO);

            String url = BASE_URI + InternalOrderApi.ADD_INTERNAL_ORDER;
            ResponseEntity<InternalOrderDTO> responseEntity = testRestTemplate.postForEntity(url, requestEntity, InternalOrderDTO.class);

            Assertions.assertThat(responseEntity).isNotNull();
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            InternalOrderDTO internalOrderSavedDTO = responseEntity.getBody();

            Assertions.assertThat(internalOrderSavedDTO).isNotNull();

            idForUpload = internalOrderSavedDTO.getId();
        }
        {
            String url = BASE_URI + InternalOrderApiController.INTERNAL_ORDER_FILE_UPLOAD;

            InternalOrderFileDTO internalOrderFileDTO = InternalOrderFileDTO.builder().name("IOF-1").notes("bla-bla-bla").build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("internal-order-file", internalOrderFileDTO);
            form.add("file", new FileSystemResource(new File("README.md")));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, httpHeaders);

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("internal-order-id", idForUpload);

            ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, requestEntity, String.class, urlVariables);

            Assertions.assertThat(responseEntity).isNotNull();
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    @Test
    @DisplayName("Test addPersonalComputerMultipart")
    @Transactional // a Session is needed (otherwise: failed to lazily initialize)
    public void addPersonalComputerMultipartTest() {
        String BASE_URI = "http://localhost:" + port;

        String idForDownload;
        String personalComputerSavedId;
        {
            String url = BASE_URI + InternalOrderApiController.ADD_INTERNAL_ORDER_MULTIPART;

            InternalOrderDTO internalOrderDTO = InternalOrderDTO.builder().personalComputer(
                    PersonalComputerDTO.builder().computerCase("PC-computerCase").build()
            ).clientData(
                    ClientDataDTO.builder().name("CD-name").surname("CD-surname").build()
            ).internalOrderFiles(List.of(InternalOrderFileDTO.builder().id("UUID-FILE-1").name("test-file-pom.xml").build())).build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("internal-order", internalOrderDTO);
            form.add("UUID-FILE-1", new FileSystemResource(new File("pom.xml")));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, httpHeaders);

            ResponseEntity<InternalOrderDTO> responseEntity = testRestTemplate.postForEntity(url, requestEntity, InternalOrderDTO.class);

            Assertions.assertThat(responseEntity).isNotNull();
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            InternalOrderDTO personalComputerSavedDTO = responseEntity.getBody();
            Assertions.assertThat(personalComputerSavedDTO).isNotNull();

            personalComputerSavedId = personalComputerSavedDTO.getId();

            List<InternalOrderFileDTO> personalComputerFiles = personalComputerSavedDTO.getInternalOrderFiles();

            Assertions.assertThat(personalComputerFiles).isNotNull();
            Assertions.assertThat(personalComputerFiles.size()).isEqualTo(1);

            idForDownload = personalComputerFiles.get(0).getId();
        }
        {
            String url = BASE_URI + InternalOrderApiController.INTERNAL_ORDER_FILE_DOWNLOAD;

            Map<String, String> urlVariables = new HashMap<>();
            urlVariables.put("metadata-id", idForDownload);

            ResponseEntity<byte[]> responseEntity = testRestTemplate.getForEntity(url, byte[].class, urlVariables);

            Assertions.assertThat(responseEntity).isNotNull();
            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

            byte[] fileByteArray = responseEntity.getBody();
            Assertions.assertThat(fileByteArray).isNotNull();
        }
        {
            Optional<InternalOrderEntity> internalOrderEntityOptional = internalOrderRepository.findById(personalComputerSavedId);

            internalOrderEntityOptional.ifPresentOrElse(internalOrderEntity -> {
                Assertions.assertThat(internalOrderEntity).isNotNull();
                Assertions.assertThat(internalOrderEntity.getId()).isEqualTo(personalComputerSavedId);

                Set<InternalOrderFileMetadataEntity> internalOrderFileMetadataEntities = internalOrderEntity.getInternalOrderFileMetadataEntities();

                Assertions.assertThat(internalOrderFileMetadataEntities).isNotNull();
                Assertions.assertThat(internalOrderFileMetadataEntities.size()).isEqualTo(1);

                internalOrderFileMetadataEntities.forEach(candidateFileEntity -> {
                    Assertions.assertThat(candidateFileEntity.getId()).isNotEqualTo(candidateFileEntity.getInternalOrderEntity().getId());
                });
            }, () -> {
                throw new RuntimeException("No personal computer was found");
            });
        }
    }

    //    @Test
//    @DisplayName("Test download")
//    @Transactional
//    public void downloadTest() {
//        ResponseEntity<byte[]> responseEntity = ((PersonalComputerApiController) personalComputerApi).download("test-personal-computer-file-metadata-entity-id-2");
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        byte[] bytes = responseEntity.getBody();
//        Assertions.assertThat(bytes).isNotNull();
//    }

    @BeforeEach
    private void initializeDatabase() {
        Assertions.assertThat(port).isNotNull();
        Assertions.assertThat(testRestTemplate).isNotNull();
        Assertions.assertThat(internalOrderRepository).isNotNull();
        Assertions.assertThat(testEntityManager).isNotNull();
        Assertions.assertThat(internalOrderApi).isNotNull();
    }
}

//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private PersonalComputerApi personalComputerApi;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Autowired
//    private PersonalComputerRepository personalComputerRepository;
//
//    @Test
//    @DisplayName("Test deletePersonalComputer")
//    public void deleteCandidateTest() {
//        ResponseEntity<Object> responseEntity = personalComputerApi.deletePersonalComputer("test-personal-computer-id-2");
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        ResponseEntity<Object> responseEntityNotFound = personalComputerApi.deletePersonalComputer("test-personal-computer-id-2");
//
//        Assertions.assertThat(responseEntityNotFound).isNotNull();
//        Assertions.assertThat(responseEntityNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//
//    @Test
//    @DisplayName("Test addPersonalComputer")
//    public void addPersonalComputerTest() {
//        PersonalComputerDTO personalComputerDTO = PersonalComputerDTO.builder().computerCase("test-new-computer-case-1").build();
//        ResponseEntity<PersonalComputerDTO> responseEntity = personalComputerApi.addPersonalComputer(personalComputerDTO);
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerSavedDTO = responseEntity.getBody();
//
//        Assertions.assertThat(personalComputerSavedDTO).isNotNull();
//        Assertions.assertThat(personalComputerSavedDTO.getId()).isNotNull();
//        Assertions.assertThat(personalComputerSavedDTO.getVersion()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Test getPersonalComputerList")
//    public void getPersonalComputersTest() {
//        ResponseEntity<List<PersonalComputerShortDTO>> responseEntity = personalComputerApi.getPersonalComputerList();
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<PersonalComputerShortDTO> personalComputerShortDTOList = responseEntity.getBody();
//
//        Assertions.assertThat(personalComputerShortDTOList).isNotNull();
//        Assertions.assertThat(personalComputerShortDTOList.size()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("Test updateCandidate 1")
//    @Transactional
//    public void updateCandidateTest1() {
//        ResponseEntity<PersonalComputerDTO> responseEntity = personalComputerApi.getPersonalComputer("test-personal-computer-id-1");
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerDTO = responseEntity.getBody();
//        Assertions.assertThat(personalComputerDTO).isNotNull();
//
//        personalComputerDTO.setComputerCase(personalComputerDTO.getComputerCase() + "-updated");
//
//        ResponseEntity<PersonalComputerDTO> responseEntityUpdated = personalComputerApi.updateCandidate(personalComputerDTO);
//        Assertions.assertThat(responseEntityUpdated).isNotNull();
//        Assertions.assertThat(responseEntityUpdated.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerUpdatedDTO = responseEntityUpdated.getBody();
//        Assertions.assertThat(personalComputerUpdatedDTO).isNotNull();
//
//        Assertions.assertThat(personalComputerUpdatedDTO.getComputerCase()).isEqualTo(personalComputerDTO.getComputerCase());
//        Assertions.assertThat(personalComputerUpdatedDTO.getVersion()).isNotEqualTo(personalComputerDTO.getVersion());
//    }
//
//    @Test
//    @DisplayName("Test updateCandidate 2")
//    @Transactional
//    public void updateCandidateTest2() {
//        ResponseEntity<PersonalComputerDTO> responseEntity = personalComputerApi.getPersonalComputer("test-personal-computer-id-2");
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerDTO = responseEntity.getBody();
//        Assertions.assertThat(personalComputerDTO).isNotNull();
//
//        personalComputerDTO.setComputerCase(personalComputerDTO.getComputerCase() + "-updated");
//
//        ResponseEntity<PersonalComputerDTO> responseEntityUpdated = personalComputerApi.updateCandidate(personalComputerDTO);
//        Assertions.assertThat(responseEntityUpdated).isNotNull();
//        Assertions.assertThat(responseEntityUpdated.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerUpdatedDTO = responseEntityUpdated.getBody();
//        Assertions.assertThat(personalComputerUpdatedDTO).isNotNull();
//
//        Assertions.assertThat(personalComputerUpdatedDTO.getComputerCase()).isEqualTo(personalComputerDTO.getComputerCase());
//        Assertions.assertThat(personalComputerUpdatedDTO.getVersion()).isNotEqualTo(personalComputerDTO.getVersion());
//    }
//
//    @Test
//    @DisplayName("Test getPersonalComputer")
//    @Transactional
//    public void getPersonalComputerTest() {
//        ResponseEntity<PersonalComputerDTO> responseEntity = personalComputerApi.getPersonalComputer("test-personal-computer-id-2");
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        PersonalComputerDTO personalComputerDTO = responseEntity.getBody();
//
//        Assertions.assertThat(personalComputerDTO).isNotNull();
//        Assertions.assertThat(personalComputerDTO.getId()).isEqualTo("test-personal-computer-id-2");
//        Assertions.assertThat(personalComputerDTO.getComputerCase()).isEqualTo("test-personal-computer-computerCase-2");
//    }
//

//

//
//    @BeforeEach
//    private void initializeDatabase() {
//        Assertions.assertThat(port).isNotNull();
//        Assertions.assertThat(personalComputerApi).isNotNull();
//        Assertions.assertThat(testRestTemplate).isNotNull();
//        Assertions.assertThat(personalComputerRepository).isNotNull();
//
//        PersonalComputerEntity personalComputerEntity1 = PersonalComputerEntity.builder().id("test-personal-computer-id-1").computerCase("test-personal-computer-computerCase-1").motherboard("test-personal-computer-motherboard-1").processor("test-personal-computer-processor-1").graphicsCard("test-personal-computer-graphicsCard-1").build();
//
////        Set<InternalOrderFileMetadataEntity> personalComputerFileMetadataEntities = new HashSet<>();
////        personalComputerFileMetadataEntities.add(InternalOrderFileMetadataEntity.builder().id("test-personal-computer-file-metadata-entity-id-2").name("test-personal-computer-file-metadata-entity-name-2").type("test-personal-computer-file-metadata-entity-type-2").personalComputerFilePayloadEntity(
////                InternalOrderFilePayloadEntity.builder().id("test-personal-computer-file-metadata-entity-id-2").mimeType("application/xml").payload(new byte[3]).build()
////        ).build());
////
////        PersonalComputerEntity personalComputerEntity2 = PersonalComputerEntity.builder().id("test-personal-computer-id-2").computerCase("test-personal-computer-computerCase-2").motherboard("test-personal-computer-motherboard-2").processor("test-personal-computer-processor-2").graphicsCard("test-personal-computer-graphicsCard-2").personalComputerFileMetadataEntities(personalComputerFileMetadataEntities).build();
//        PersonalComputerEntity personalComputerEntity3 = PersonalComputerEntity.builder().id("test-personal-computer-id-3").computerCase("test-personal-computer-computerCase-3").motherboard("test-personal-computer-motherboard-3").processor("test-personal-computer-processor-3").graphicsCard("test-personal-computer-graphicsCard-3").dateOfDeletion(new Timestamp(System.currentTimeMillis())).build();
//
//        personalComputerRepository.saveAllAndFlush(List.of(personalComputerEntity1,
////                personalComputerEntity2,
//                personalComputerEntity3));
//    }