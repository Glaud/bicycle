package lv.proofit.bicycle.engine.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@ToString(callSuper = true)
public class SumInsuredCalculationRequest extends CalculationRequest {

    public SumInsuredCalculationRequest(String make, String model, Integer year, String risk, int count, BigDecimal sumInsured) {
        super(make, model, year, risk, count, sumInsured);
    }
}
