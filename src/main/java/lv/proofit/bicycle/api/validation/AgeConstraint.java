package lv.proofit.bicycle.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeConstraint {

    int maxAge();

    String message() default "Bicycle is too old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
