package lv.proofit.bicycle.engine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.proofit.bicycle.api.model.BicyclePolicy;
import lv.proofit.bicycle.api.model.PremiumObject;
import lv.proofit.bicycle.api.model.PremiumRequest;
import lv.proofit.bicycle.api.model.PremiumResponse;
import lv.proofit.bicycle.engine.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class Calculator {

    private final ScriptExecutor scriptExecutor;

    public PremiumResponse calculatePremiums(PremiumRequest premiumRequest) {
        var bicyclesCount = premiumRequest.bicycles().size();
        var premiumObjects = premiumRequest.bicycles().stream().map(bicycle -> calculatePremiumsForBicycle(bicycle, bicyclesCount)).toList();
        var totalPremium = premiumObjects.stream().map(PremiumObject::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new PremiumResponse(premiumObjects, totalPremium);
    }

    private PremiumObject calculatePremiumsForBicycle(BicyclePolicy bicyclePolicy, int bicyclesCount) {
        log.info("Received {}", bicyclePolicy);
        var calculationRequests = bicyclePolicy.getRisks().stream().map(riskType -> createCalculationRequests(bicyclePolicy, riskType, bicyclesCount)).toList();
        PremiumObject premiumObject = new PremiumObject(createAttributes(bicyclePolicy), bicyclePolicy.getCoverage(), bicyclePolicy.getSumInsured());
        calculationRequests.forEach(calculationRequest -> {
            var riskSumInsured = scriptExecutor.calculate(calculationRequest, CalculationType.SUM_INSURED);
            var premium = scriptExecutor.calculate(new PremiumCalculationRequest(calculationRequest, riskSumInsured), CalculationType.PREMIUM);
            premiumObject.addPremium(premium);
            premiumObject.addRisk(new RiskResponse(calculationRequest.getRisk(), riskSumInsured, premium));
        });

        return premiumObject;
    }

    private Map<String, String> createAttributes(BicyclePolicy bicyclePolicy) {
        return Map.of("MANUFACTURE_YEAR", String.valueOf(bicyclePolicy.getManufactureYear()), "MODEL", bicyclePolicy.getModel(), "MAKE", bicyclePolicy.getMake());
    }

    private CalculationRequest createCalculationRequests(BicyclePolicy bicyclePolicy, String riskType, int bicyclesCount) {
        return new SumInsuredCalculationRequest(bicyclePolicy.getMake(), bicyclePolicy.getModel(), bicyclePolicy.getManufactureYear(), riskType, bicyclesCount, bicyclePolicy.getSumInsured());
    }


}
