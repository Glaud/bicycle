package lv.proofit.bicycle.api.model;

import lombok.Getter;
import lombok.ToString;
import lv.proofit.bicycle.engine.model.CoverageType;

import java.math.BigDecimal;
import java.util.Set;

@ToString
@Getter
public class BicyclePolicy {

    private String make;
    private String model;
    private Integer manufactureYear;
    private CoverageType coverage;
    private BigDecimal sumInsured;
    private Set<String> risks = Set.of("THEFT");
}
