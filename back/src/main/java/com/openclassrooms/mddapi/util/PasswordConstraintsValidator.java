package com.openclassrooms.mddapi.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        PasswordValidator passwordValidator = new PasswordValidator(
                Arrays.asList(
                        new LengthRule(6, 60),
                        // At least one upper case letter
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        // At least one number
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        // At least one special character
                        new CharacterRule(EnglishCharacterData.Special, 1),
                        // No whitespace allowed
                        new WhitespaceRule()));


        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {

            return true;

        }

        //Sending one message each time failed validation.
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(passwordValidator
                        .getMessages(result).stream().findFirst().get())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;

    }
}
