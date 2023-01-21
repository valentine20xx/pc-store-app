package de.niko.pcstore.controller.api;

import de.niko.pcstore.controller.experimentalapi.InternalOrderApiAdvancedController;
import de.niko.pcstore.entity.InternalOrderEntity;
import de.niko.pcstore.repository.InternalOrderRepository;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test InternalOrderApiAdvancedController")
public class InternalOrderApiAdvancedControllerTest {
    @MockBean
    private InternalOrderRepository internalOrderRepository;
    @Autowired
    private InternalOrderApiAdvancedController internalOrderApiAdvancedController;

    @BeforeEach
    public void beforeTest() {
        Mockito.reset(internalOrderRepository);
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

        ResponseEntity<Object> objectResponseEntity = internalOrderApiAdvancedController.addInternalOrderMultipart(standardMultipartHttpServletRequest);

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
