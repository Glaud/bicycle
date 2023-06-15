package lv.proofit.bicycle.api.model;

import lombok.Getter;
import lombok.ToString;
import lv.proofit.bicycle.engine.model.CoverageType;
import lv.proofit.bicycle.engine.model.RiskResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class PremiumObject {

    private Map<String, String> attributes;
    private CoverageType coverageType;
    private List<RiskResponse> risks = new ArrayList<>();
    private BigDecimal sumInsured;
    private BigDecimal premium = BigDecimal.ZERO;

    public PremiumObject(Map<String, String> attributes, CoverageType coverageType, BigDecimal sumInsured) {
        this.attributes = attributes;
        this.coverageType = coverageType;
        this.sumInsured = sumInsured;
    }

    public void addRisk(RiskResponse riskResponse) {
        this.risks.add(riskResponse);
    }

    public void addPremium(BigDecimal premium) {
        this.premium = this.premium.add(premium);
    }

}
