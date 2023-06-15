package lv.proofit.bicycle.controller;

import lombok.RequiredArgsConstructor;
import lv.proofit.bicycle.engine.Calculator;
import lv.proofit.bicycle.engine.model.PremiumRequest;
import lv.proofit.bicycle.engine.model.PremiumResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final Calculator calculator;

    @PostMapping
    PremiumResponse calculate(@RequestBody PremiumRequest premiumRequest) {
        return calculator.calculatePremiums(premiumRequest);
    }
}