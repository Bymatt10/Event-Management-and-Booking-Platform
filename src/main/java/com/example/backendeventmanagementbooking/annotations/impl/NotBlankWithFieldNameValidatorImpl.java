package com.example.backendeventmanagementbooking.annotations.impl;

import com.example.backendeventmanagementbooking.annotations.NotBlankWithFieldName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import static com.example.backendeventmanagementbooking.config.ConstantsVariables.INVALID_STRING_NOT_BLANK;

public class NotBlankWithFieldNameValidatorImpl  implements ConstraintValidator<NotBlankWithFieldName, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null || value.toString().trim().isEmpty()) {
            var fieldName = ((ConstraintValidatorContextImpl) context)
                    .getConstraintViolationCreationContexts()
                    .getFirst()
                    .getPath()
                    .asString();

            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldName + INVALID_STRING_NOT_BLANK)
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
