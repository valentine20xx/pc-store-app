package de.niko.pcstore.controller.api;

import de.niko.pcstore.configuration.MapperConfiguration;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.repository.InternalOrderRepository;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test InternalOrderApiController")
@Import(MapperConfiguration.class)
public class InternalOrderApiControllerTest {
    @MockBean
    private InternalOrderRepository internalOrderRepository;
    @Autowired
    private InternalOrderApiController internalOrderApiController;

    @BeforeEach
    public void beforeTest() {
        Mockito.reset(internalOrderRepository);
    }

    @Test
    @DisplayName("Test getInternalOrderList without statuses")
    public void getInternalOrderList() {
        InternalOrderEntity internalOrderEntity1 = new InternalOrderEntity();
        internalOrderEntity1.setId(UUID.randomUUID().toString());
        internalOrderEntity1.setVersion(Timestamp.from(Instant.now()));

        InternalOrderEntity internalOrderEntity2 = new InternalOrderEntity();
        internalOrderEntity2.setId(UUID.randomUUID().toString());
        internalOrderEntity2.setVersion(Timestamp.from(Instant.now()));

        Mockito.when(internalOrderRepository.findAll()).thenReturn(Arrays.asList(internalOrderEntity1, internalOrderEntity2));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, AuthorityUtils.createAuthorityList("ROLE_READ", "ROLE_EDIT")));

        ResponseEntity<Object> internalOrderListResponseEntity = internalOrderApiController.getInternalOrderList(Collections.emptyList());

        Assertions.assertNotNull(internalOrderListResponseEntity);
        Assertions.assertEquals(HttpStatus.OK, internalOrderListResponseEntity.getStatusCode());

        List<InternalOrderShortDTO> internalOrderList = (List<InternalOrderShortDTO>) internalOrderListResponseEntity.getBody();

        Assertions.assertNotNull(internalOrderList);
        Assertions.assertEquals(2, internalOrderList.size());
    }

    @Test
    @DisplayName("Test getInternalOrder")
    public void getInternalOrder() {
        String ID = UUID.randomUUID().toString();

        InternalOrderEntity internalOrderEntity1 = new InternalOrderEntity();
        internalOrderEntity1.setId(ID);
        internalOrderEntity1.setVersion(Timestamp.from(Instant.now()));

        Mockito.when(internalOrderRepository.findById(Mockito.eq(ID))).thenReturn(Optional.of(internalOrderEntity1));

        ResponseEntity<InternalOrderDTO> internalOrderDTOResponseEntity = internalOrderApiController.getInternalOrder(ID);

        Assertions.assertNotNull(internalOrderDTOResponseEntity);
        Assertions.assertEquals(HttpStatus.OK, internalOrderDTOResponseEntity.getStatusCode());

        Assertions.assertNotNull(internalOrderDTOResponseEntity.getBody());
        Assertions.assertEquals(ID, internalOrderDTOResponseEntity.getBody().getId());
    }


    @Test
    @DisplayName("Test addInternalOrderMultipart")
    public void addInternalOrderMultipartTest() throws Exception {
        StandardMultipartHttpServletRequest standardMultipartHttpServletRequest = Mockito.mock(StandardMultipartHttpServletRequest.class);

        Assertions.assertNotNull(standardMultipartHttpServletRequest);

        InternalOrderEntity internalOrderEntity1 = new InternalOrderEntity();
        internalOrderEntity1.setId(UUID.randomUUID().toString());

        File addInternalOrderMultipartFile = ResourceUtils.getFile("classpath:addInternalOrderMultipart.json");
        String addInternalOrderMultipartJson = FileUtils.readFileToString(addInternalOrderMultipartFile, StandardCharsets.UTF_8);

        Mockito.when(standardMultipartHttpServletRequest.getParameter(Mockito.eq("internal-order"))).thenReturn(addInternalOrderMultipartJson);

        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        Mockito.when(multipartFile.getContentType()).thenReturn("TestContentType");

        Mockito.when(standardMultipartHttpServletRequest.getFile(Mockito.eq("testId1"))).thenReturn(multipartFile);
        Mockito.when(internalOrderRepository.saveAndFlush(Mockito.any())).thenReturn(internalOrderEntity1);

        ResponseEntity<Object> objectResponseEntity = internalOrderApiController.addInternalOrderMultipart(standardMultipartHttpServletRequest);

        Mockito.verify(internalOrderRepository, data -> {
            List<Invocation> invocations = data.getAllInvocations();

            Assertions.assertEquals(1, invocations.size());
            InternalOrderEntity entity = (InternalOrderEntity) invocations.get(0).getArguments()[0];

            Assertions.assertNotNull(entity);
            Assertions.assertNull(entity.getId());

            Assertions.assertNotNull(entity.getClientData());
            Assertions.assertEquals("json1", entity.getClientData().getName());

            Assertions.assertNotNull(entity.getPersonalComputer());

            Assertions.assertNotNull(entity.getInternalOrderFileMetadataEntities());
            Assertions.assertEquals(1, entity.getInternalOrderFileMetadataEntities().size());

            entity.getInternalOrderFileMetadataEntities().forEach(internalOrderFileMetadataEntity -> {
                Assertions.assertNull(internalOrderFileMetadataEntity.getId());
                Assertions.assertNotNull(internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity());
                Assertions.assertNull(internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity().getId());
                Assertions.assertNotNull(internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity().getPayload());
                Assertions.assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}, internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity().getPayload());
                Assertions.assertNotNull(internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity().getMimeType());
                Assertions.assertEquals("TestContentType", internalOrderFileMetadataEntity.getInternalOrderFilePayloadEntity().getMimeType());

            });
        }).saveAndFlush(Mockito.any());

        Assertions.assertNotNull(objectResponseEntity);
        Assertions.assertEquals(HttpStatus.OK, objectResponseEntity.getStatusCode());
    }
}


//    @Test
//    @DisplayName("Test updateInternalOrderStatus")
//    @Transactional
//    @Sql("/test-data.sql")
//    @Disabled
//    public void updateInternalOrderStatusTest() {
////        String BASE_URI = "http://localhost:" + port;
////        String url = BASE_URI + InternalOrderApi.UPDATE_INTERNAL_ORDER_STATUS;
////        Map<String, String> urlVariables = new HashMap<>();
////        urlVariables.put("id", "INTERNAL_ORDER_ID_1");
////        urlVariables.put("status", "producing");
////        ResponseEntity<Object> responseEntity = testRestTemplate.getForEntity(url + "?id=INTERNAL_ORDER_ID_1&status=producing", Object.class);
//        {
//            ResponseEntity<InternalOrderDTO> responseEntity = internalOrderApi.getInternalOrder("INTERNAL_ORDER_ID_1");
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//            InternalOrderDTO internalOrderSavedDTO = responseEntity.getBody();
//
//            Assertions.assertThat(internalOrderSavedDTO).isNotNull();
//            Assertions.assertThat(internalOrderSavedDTO.getStatus()).isEqualTo(InternalOrderDTO.Status.OPEN);
//        }
//        {
//            ResponseEntity<Object> responseEntity = internalOrderApi.updateInternalOrderStatus("INTERNAL_ORDER_ID_1", InternalOrderDTO.Status.PRODUCING);
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        }
//        {
//            ResponseEntity<InternalOrderDTO> responseEntity = internalOrderApi.getInternalOrder("INTERNAL_ORDER_ID_1");
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//            InternalOrderDTO internalOrderSavedDTO = responseEntity.getBody();
//
//            Assertions.assertThat(internalOrderSavedDTO).isNotNull();
//            Assertions.assertThat(internalOrderSavedDTO.getStatus()).isEqualTo(InternalOrderDTO.Status.PRODUCING);
//        }
//        {
//            ResponseEntity<Object> responseEntity = internalOrderApi.updateInternalOrderStatus("INTERNAL_ORDER_ID_1", InternalOrderDTO.Status.PRODUCING);
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);
//        }
//    }
//
//    @Test
//    @DisplayName("Test upload")
//    @Transactional
//    @Sql("/test-data.sql")
//    @Disabled
//    public void uploadTest() {
//        String BASE_URI = "http://localhost:";
//
//        String idForUpload;
//        {
//            NewInternalOrderDTO internalOrderDTO = NewInternalOrderDTO.builder().clientData(
//                    NewInternalOrderDTO.NewClientDataDTO.builder().name("CD-name-1").surname("CD-name-2").build()
//            ).personalComputer(
//                    NewInternalOrderDTO.NewPersonalComputerDTO.builder().processor("PC-processor-1").graphicsCard("PC-graphicsCard-1").build()
//            ).build();
//            HttpEntity<NewInternalOrderDTO> requestEntity = new HttpEntity<>(internalOrderDTO);
//
//            String url = BASE_URI + InternalOrderApi.ADD_INTERNAL_ORDER;
//            ResponseEntity<InternalOrderDTO> responseEntity = testRestTemplate.postForEntity(url, requestEntity, InternalOrderDTO.class);
////            URI uri = testRestTemplate.postForLocation(url, requestEntity);
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//            InternalOrderDTO internalOrderSavedDTO = responseEntity.getBody();
//
//            Assertions.assertThat(internalOrderSavedDTO).isNotNull();
//
//            idForUpload = internalOrderSavedDTO.getId();
//        }
//        {
//            String url = BASE_URI + InternalOrderApiController.INTERNAL_ORDER_FILE_UPLOAD;
//
//            NewInternalOrderFileDTO internalOrderFileDTO = NewInternalOrderFileDTO.builder().name("IOF-1").note("bla-bla-bla").build();
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
//            form.add("internal-order-file", internalOrderFileDTO);
//            form.add("file", new FileSystemResource(new File("README.md")));
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, httpHeaders);
//
//            Map<String, String> urlVariables = new HashMap<>();
//            urlVariables.put("internal-order-id", idForUpload);
//
//            ResponseEntity<String> responseEntity = testRestTemplate.postForEntity(url, requestEntity, String.class, urlVariables);
//
//            Assertions.assertThat(responseEntity).isNotNull();
//            Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        }
//    }
//

//
//    @BeforeEach
//    private void initializeDatabase() {
//        Assertions.assertThat(testRestTemplate).isNotNull();
//        Assertions.assertThat(internalOrderRepository).isNotNull();
//        Assertions.assertThat(testEntityManager).isNotNull();
//        Assertions.assertThat(internalOrderApi).isNotNull();
//    }
//
//    public static class RestTemplateLogger implements ClientHttpRequestInterceptor {
//        private static final Logger LOG = LoggerFactory.getLogger(RestTemplateLogger.class);
//        private static final String RTLM00001E = "RTLM00001E API Request per rest error " +
//                "[Request: URI='{}', Method='{}', Headers='{}', Body='{}']" +
//                "[Response: Code='{}', Text='{}', Headers='{}', Body='{}']";
//        private static final String RTLM00001I = "RTLM00001I API Request per rest info " +
//                "[Request: URI='{}', Method='{}', Headers='{}', Body='{}']" +
//                "[Response: Code='{}', Text='{}', Headers='{}', Body='{}']";
//
//        @Override
//        public ClientHttpResponse intercept(HttpRequest request, byte[] requestBodyBytes, ClientHttpRequestExecution execution) throws IOException {
//            HttpHeaders requestHeaders = request.getHeaders();
//            String requestMethod = request.getMethodValue();
//            URI requestURI = request.getURI();
//            String requestBody = new String(requestBodyBytes, Charset.defaultCharset());
//
//            ClientHttpResponse response = execution.execute(request, requestBodyBytes);
//
//            HttpStatus responseStatusCode = response.getStatusCode();
//            String responseStatusText = response.getStatusText();
//            HttpHeaders responseHeaders = response.getHeaders();
//            InputStream responseBody = response.getBody();
//            String responseBodyAsString = StreamUtils.copyToString(responseBody, Charset.defaultCharset());
//
//            if (responseStatusCode.isError()) {
//                LOG.error(RTLM00001E, requestURI, requestMethod, requestHeaders, requestBody, responseStatusCode, responseStatusText, responseHeaders, responseBodyAsString);
//            } else {
//                LOG.info(RTLM00001I, requestURI, requestMethod, requestHeaders, requestBody, responseStatusCode, responseStatusText, responseHeaders, responseBodyAsString);
//            }
//
//            return response;
//        }
//    }
//
//    @Test
//    @DisplayName("Test addPersonalComputer")
//    @Disabled
//    public void addPersonalComputerTest() {
//        InternalOrderDTO.PersonalComputerDTO personalComputerDTO = InternalOrderDTO.PersonalComputerDTO.builder().computerCase("test-new-computer-case-1").build();
////        ResponseEntity<InternalOrderDTO.PersonalComputerDTO> responseEntity = personalComputerApi.addPersonalComputer(personalComputerDTO);
//        String BASE_URI = "http://localhost:";
//        String url = BASE_URI + InternalOrderApiController.ADD_INTERNAL_ORDER;
//
//        NewInternalOrderDTO internalOrderDTO = new NewInternalOrderDTO();
//
//        ResponseEntity<InternalOrderDTO> responseEntity = testRestTemplate.postForEntity(url, internalOrderDTO, InternalOrderDTO.class);
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
////        InternalOrderDTO.PersonalComputerDTO personalComputerSavedDTO = responseEntity.getBody();
////
////        Assertions.assertThat(personalComputerSavedDTO).isNotNull();
////        Assertions.assertThat(personalComputerSavedDTO.getId()).isNotNull();
////        Assertions.assertThat(personalComputerSavedDTO.getVersion()).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Test getPersonalComputerList")
//    @Disabled
//    public void getPersonalComputersTest() {
//        String BASE_URI = "http://localhost:";
//        String url = BASE_URI + InternalOrderApiController.GET_INTERNAL_ORDER_LIST;
//
//        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity(url, List.class);
//
//        Assertions.assertThat(responseEntity).isNotNull();
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<InternalOrderShortDTO> personalComputerShortDTOList = (List<InternalOrderShortDTO>) responseEntity.getBody();
//
//        Assertions.assertThat(personalComputerShortDTOList).isNotNull();
//        Assertions.assertThat(personalComputerShortDTOList.size()).isEqualTo(0);
//    }
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

//

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