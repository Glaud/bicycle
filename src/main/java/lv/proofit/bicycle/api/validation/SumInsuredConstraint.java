package lv.proofit.bicycle.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SumInsuredValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SumInsuredConstraint {

    String maxSumInsured();

    String message() default "Sum insured amount is too big";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
