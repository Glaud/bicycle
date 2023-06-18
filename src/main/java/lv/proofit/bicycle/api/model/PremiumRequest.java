package lv.proofit.bicycle.api.model;

import jakarta.validation.Valid;

import java.util.List;

public record PremiumRequest(@Valid List<BicyclePolicy> bicycles) {
}
