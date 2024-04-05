package com.openclassrooms.mddapi.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

/**
 * PasswordConstraintsValidator is a class that validates password constraints.
 * It implements the ConstraintValidator interface and overrides the isValid method.
 * It uses the Passay library to validate the password according to the specified rules.
 */
public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    /**
     * Checks if the given password is valid according to the specified rules.
     * The rules are: minimum length of 6, maximum length of 60, at least one uppercase letter,
     * at least one digit, at least one special character, and no whitespace.
     *
     * @param password                   the password to be validated
     * @param constraintValidatorContext the context in which the constraint is defined
     * @return true if the password is valid, false otherwise
     */
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
