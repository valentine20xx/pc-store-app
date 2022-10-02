package de.niko.pcstore.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.MessageCodesResolver;

@DisplayName("Test NewInternalOrderMPDTOValidator")
@SpringBootTest
public class NewInternalOrderMPDTOValidatorTest {

    @Autowired
    @Qualifier("myMessageCodesResolver")
    private MessageCodesResolver myMessageCodesResolver;

    @BeforeEach
    private void initCheck() {
        Assertions.assertThat(myMessageCodesResolver).isNotNull();
    }

    @Test
    @DisplayName("Test validate")
    public void validateTest() {
        NewInternalOrderMPDTOValidator newInternalOrderMPDTOValidator = new NewInternalOrderMPDTOValidator();

        Assertions.assertThat(newInternalOrderMPDTOValidator).isNotNull();

        NewInternalOrderMPDTO newInternalOrderMPDTO = new NewInternalOrderMPDTO();
        newInternalOrderMPDTO.setClientData(new NewInternalOrderMPDTO.NewClientDataMPDTO());

        DataBinder dataBinder = new DataBinder(newInternalOrderMPDTO);
        dataBinder.addValidators(newInternalOrderMPDTOValidator);
        dataBinder.setMessageCodesResolver(myMessageCodesResolver);
        dataBinder.validate();

        BindingResult bindingResult = dataBinder.getBindingResult();

        Assertions.assertThat(bindingResult).isNotNull();
    }
}
