package lv.proofit.bicycle.api.model;

import java.util.List;

public record PremiumRequest(List<BicyclePolicy> bicycles) {
}
