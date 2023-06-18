package lv.proofit.bicycle.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class SumInsuredValidator implements ConstraintValidator<SumInsuredConstraint, BigDecimal> {

    private BigDecimal maxSumInsured;

    @Override
    public void initialize(SumInsuredConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.maxSumInsured = new BigDecimal(constraintAnnotation.maxSumInsured());
    }

    @Override
    public boolean isValid(BigDecimal sumInsured, ConstraintValidatorContext constraintValidatorContext) {
        return sumInsured.compareTo(this.maxSumInsured) <= 0;
//        }
    }
}
