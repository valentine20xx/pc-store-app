package de.niko.pcstore.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class NewInternalOrderMPDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NewInternalOrderMPDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        ValidationUtils.rejectIfEmpty(errors, "date", "date.empty");
//        ValidationUtils.rejectIfEmpty(errors, "price", "price.empty");

        NewInternalOrderMPDTO newInternalOrderMPDTO = (NewInternalOrderMPDTO) target;

        NewInternalOrderMPDTO.NewClientDataMPDTO newClientDataMPDTO = newInternalOrderMPDTO.getClientData();

        Integer zip = newClientDataMPDTO.getZip();
        if (zip == null) {
//            errors.reject("zip", "zip must not be null");
            ValidationUtils.rejectIfEmpty(errors, "clientData.zip", "clientData.zip.empty", "zip must not be null");
        }
//        if (order.getPrice() != null &&
//                order.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
//            errors.rejectValue("price", "price.invalid");
//        }

    }
}
