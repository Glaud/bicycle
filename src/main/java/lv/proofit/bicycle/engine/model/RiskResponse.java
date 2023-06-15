package lv.proofit.bicycle.engine.model;

import java.math.BigDecimal;

public record RiskResponse(String riskType, BigDecimal sumInsured, BigDecimal premium) {
}
