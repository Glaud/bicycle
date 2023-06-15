package lv.proofit.bicycle.engine.model;

import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
public class PremiumCalculationRequest extends CalculationRequest {

    private final BigDecimal riskSumInsured;

    public PremiumCalculationRequest(CalculationRequest calculationRequest, BigDecimal riskSumInsured) {
        super(calculationRequest.getMake(), calculationRequest.getModel(), calculationRequest.getYear(), calculationRequest.getRisk(),
                calculationRequest.getCount(), calculationRequest.getSumInsured());
        this.riskSumInsured = riskSumInsured;
    }
}
