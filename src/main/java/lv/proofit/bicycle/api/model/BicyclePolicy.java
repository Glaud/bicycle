package lv.proofit.bicycle.api.model;

import lombok.*;
import lv.proofit.bicycle.api.validation.AgeConstraint;
import lv.proofit.bicycle.api.validation.SumInsuredConstraint;
import lv.proofit.bicycle.engine.model.CoverageType;

import java.math.BigDecimal;
import java.util.Set;

@ToString
@Getter
public class BicyclePolicy {

    private String make;
    private String model;
    @AgeConstraint(maxAge = 10, message = "Bicycle is older than 10 years")
    private Integer manufactureYear;
    private CoverageType coverage;
    @SumInsuredConstraint(maxSumInsured = "10000", message = "Sum insured amount is bigger than 10000")
    private BigDecimal sumInsured;
    private Set<String> risks = Set.of("THEFT");
}
