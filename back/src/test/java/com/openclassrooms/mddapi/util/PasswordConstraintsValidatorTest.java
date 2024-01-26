package com.openclassrooms.mddapi.util;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PasswordConstraintsValidatorTest {
    @InjectMocks
    private PasswordConstraintsValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidPassword() {
        String validPassword = "ValidPassword123!";

        assertTrue(validator.isValid(validPassword, context));
    }

    @Test
    void testInvalidPassword() {
        String invalidPassword = "weak";
        ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);

        when(context.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(builder);
        when(builder.addConstraintViolation()).thenReturn(context);

        assertFalse(validator.isValid(invalidPassword, context));
    }

}