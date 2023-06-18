package lv.proofit.bicycle.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PremiumRequest(@Valid @NotEmpty List<BicyclePolicy> bicycles) {
}
