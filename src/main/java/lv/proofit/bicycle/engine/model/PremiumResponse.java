package lv.proofit.bicycle.engine.model;

import java.math.BigDecimal;
import java.util.List;

public record  PremiumResponse(List<PremiumObject> objects, BigDecimal premium) {

}
