package lv.proofit.bicycle.engine.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@ToString
@Getter
@Builder
public class BicyclePolicy {

    private String make;
    private String model;
    private Integer manufactureYear;
    private CoverageType coverage;
    private BigDecimal sumInsured;
    private Set<String> risks = Set.of("THEFT");
}
