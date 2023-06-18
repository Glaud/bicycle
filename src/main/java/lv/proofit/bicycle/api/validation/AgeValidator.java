package lv.proofit.bicycle.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class AgeValidator implements ConstraintValidator<AgeConstraint, Integer> {

    private int maxAge;

    @Override
    public void initialize(AgeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxAge = constraintAnnotation.maxAge();
    }

    @Override
    public boolean isValid(Integer manufactureYear, ConstraintValidatorContext constraintValidatorContext) {
        var age = Year.now().getValue() - manufactureYear;
        return age <= maxAge;
    }
}
