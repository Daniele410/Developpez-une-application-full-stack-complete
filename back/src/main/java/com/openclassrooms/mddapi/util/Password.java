package com.openclassrooms.mddapi.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Password is a custom annotation used for password validation.
 * It uses the PasswordConstraintsValidator class to validate the annotated field.
 * The message, groups, and payload methods are part of the Constraint annotation.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintsValidator.class)
public @interface Password {

    /**
     * The message to be displayed when the annotated field fails validation.
     *
     * @return the validation failure message
     */
    String message() default "Invalid password!";

    /**
     * The groups the constraint belongs to.
     *
     * @return the groups the constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * The payload associated with the constraint.
     *
     * @return the payload associated with the constraint
     */
    Class<? extends Payload>[] payload() default {};

}
