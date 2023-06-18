package lv.proofit.bicycle.engine.service;

import lv.proofit.bicycle.engine.model.CalculationType;
import lv.proofit.bicycle.engine.model.PremiumCalculationRequest;
import lv.proofit.bicycle.engine.model.SumInsuredCalculationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ScriptExecutorIT {

    @Autowired
    private ScriptExecutor scriptExecutor;

    @Test
    void shouldCalculateSumInsuredForTheftRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "THEFT", 3, new BigDecimal("1000"));
        var actualPremium = scriptExecutor.calculate(sumInsuredCalculationRequest, CalculationType.SUM_INSURED);
        assertEquals(0, new BigDecimal("1000.00").compareTo(actualPremium));
    }

    @Test
    void shouldCalculateSumInsuredForDamageRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "DAMAGE", 3, new BigDecimal("1000"));
        var actualPremium = scriptExecutor.calculate(sumInsuredCalculationRequest, CalculationType.SUM_INSURED);
        assertEquals(0, new BigDecimal("500.00").compareTo(actualPremium));
    }
    @Test
    void shouldCalculatePremiumForSumInsuredRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "THIRD_PARTY_DAMAGE", 3, new BigDecimal("1000"));
        var actualPremium = scriptExecutor.calculate(sumInsuredCalculationRequest, CalculationType.SUM_INSURED);
        assertEquals(0, new BigDecimal("100.00").compareTo(actualPremium));
    }
    @Test
    void shouldCalculatePremiumForTheftRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "THEFT", 3, new BigDecimal("1000"));
        var premiumCalculationRequest = new PremiumCalculationRequest(sumInsuredCalculationRequest, new BigDecimal("1000.00"));
        var actualPremium = scriptExecutor.calculate(premiumCalculationRequest, CalculationType.PREMIUM);
        assertEquals(0, new BigDecimal("30.00").compareTo(actualPremium));
    }

    @Test
    void shouldCalculatePremiumForDamageRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "DAMAGE", 3, new BigDecimal("1000"));
        var premiumCalculationRequest = new PremiumCalculationRequest(sumInsuredCalculationRequest, new BigDecimal("500.00"));
        var actualPremium = scriptExecutor.calculate(premiumCalculationRequest, CalculationType.PREMIUM);
        assertEquals(0, new BigDecimal("6.96").compareTo(actualPremium));
    }
    @Test
    void shouldCalculatePremiumForThirdPartyDamageRisk() {
        var sumInsuredCalculationRequest = new SumInsuredCalculationRequest("Pearl", "Gravel SL EVO", 2015, "THIRD_PARTY_DAMAGE", 3, new BigDecimal("1000"));
        var premiumCalculationRequest = new PremiumCalculationRequest(sumInsuredCalculationRequest, new BigDecimal("100.00"));
        var actualPremium = scriptExecutor.calculate(premiumCalculationRequest, CalculationType.PREMIUM);
        assertEquals(0, new BigDecimal("12.00").compareTo(actualPremium));
    }
}