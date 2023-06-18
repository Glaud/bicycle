package lv.proofit.bicycle.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lv.proofit.bicycle.engine.service.Calculator;
import lv.proofit.bicycle.api.model.PremiumRequest;
import lv.proofit.bicycle.api.model.PremiumResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculate")
@RequiredArgsConstructor
public class CalculatorController {

    private final Calculator calculator;

    @PostMapping
    PremiumResponse calculate(@RequestBody @Valid PremiumRequest premiumRequest) {
        return calculator.calculatePremiums(premiumRequest);
    }
}
