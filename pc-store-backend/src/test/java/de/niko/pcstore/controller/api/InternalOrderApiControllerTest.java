package de.niko.pcstore.controller.api;

import de.niko.pcstore.configuration.MapperConfiguration;
import de.niko.pcstore.dto.InternalOrderDTO;
import de.niko.pcstore.dto.InternalOrderShortDTO;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.repository.InternalOrderRepository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;

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

        ResponseEntity<List<InternalOrderShortDTO>> internalOrderListResponseEntity = internalOrderApiController.getInternalOrderList(Collections.emptyList());

        Assertions.assertNotNull(internalOrderListResponseEntity);
        Assertions.assertEquals(HttpStatus.OK, internalOrderListResponseEntity.getStatusCode());

        List<InternalOrderShortDTO> internalOrderList = internalOrderListResponseEntity.getBody();

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
}

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