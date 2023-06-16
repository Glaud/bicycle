package lv.proofit.bicycle.engine.service;

import lv.proofit.bicycle.api.model.BicyclePolicy;
import lv.proofit.bicycle.api.model.PremiumRequest;
import lv.proofit.bicycle.engine.model.CalculationType;
import lv.proofit.bicycle.engine.model.CoverageType;
import lv.proofit.bicycle.engine.model.PremiumCalculationRequest;
import lv.proofit.bicycle.engine.model.SumInsuredCalculationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    private Calculator calculator;
    @Mock
    private ScriptExecutor scriptExecutor;

    private PremiumRequest premiumRequest;
    private BicyclePolicy bicyclePolicy1;
    private BicyclePolicy bicyclePolicy2;

    @BeforeEach
    void setUp() {
        calculator = new Calculator(scriptExecutor);
        setUpPremiumRequest();
    }

    @Test
    void shouldCorrectlyCalculatePremiums() {
        doAnswer((Answer<BigDecimal>) invocation -> {
            var make = invocation.getArgument(0, SumInsuredCalculationRequest.class).getMake();
            var risk = invocation.getArgument(0, SumInsuredCalculationRequest.class).getRisk();
            if ("Pearl".equals(make)) {
                if ("THEFT".equals(risk)) {
                    return new BigDecimal("1000");
                } else if ("DAMAGE".equals(risk)) {
                    return new BigDecimal("500");
                } else if ("THIRD_PARTY_DAMAGE".equals(risk)) {
                    return new BigDecimal("100");
                }
            } else if ("Sensa".equals(make)) {
                if ("DAMAGE".equals(risk)) {
                    return new BigDecimal("112.50");
                }
            }
            return null;
        }).when(scriptExecutor).calculate(any(SumInsuredCalculationRequest.class), eq(CalculationType.SUM_INSURED));
        doAnswer((Answer<BigDecimal>) invocation -> {
            var make = invocation.getArgument(0, PremiumCalculationRequest.class).getMake();
            var risk = invocation.getArgument(0, PremiumCalculationRequest.class).getRisk();
            if ("Pearl".equals(make)) {
                if ("THEFT".equals(risk)) {
                    return new BigDecimal("30");
                } else if ("DAMAGE".equals(risk)) {
                    return new BigDecimal("6.95");
                } else if ("THIRD_PARTY_DAMAGE".equals(risk)) {
                    return new BigDecimal("12");
                }
            } else if ("Sensa".equals(make)) {
                if ("DAMAGE".equals(risk)) {
                    return new BigDecimal("5.78");
                }
            }
            return null;
        }).when(scriptExecutor).calculate(any(PremiumCalculationRequest.class), eq(CalculationType.PREMIUM));
        var actualPremiumResponse = calculator.calculatePremiums(premiumRequest);
        assertEquals(2, actualPremiumResponse.objects().size());
        var optActualObject1 = actualPremiumResponse.objects().stream().filter(o -> o.getAttributes().get("MAKE").equals("Pearl")).findFirst();
        var optActualObject2 = actualPremiumResponse.objects().stream().filter(o -> o.getAttributes().get("MAKE").equals("Sensa")).findFirst();
        assertTrue(optActualObject1.isPresent());
        assertTrue(optActualObject2.isPresent());
        var actualObject1 = optActualObject1.get();
        var actualObject2 = optActualObject2.get();
        assertEquals("Pearl", actualObject1.getAttributes().get("MAKE"));
        assertEquals("2015", actualObject1.getAttributes().get("MANUFACTURE_YEAR"));
        assertEquals("Gravel SL EVO", actualObject1.getAttributes().get("MODEL"));
        assertEquals(CoverageType.EXTRA, actualObject1.getCoverageType());
        assertEquals(3, actualObject1.getRisks().size());
        var optRisk1 = actualObject1.getRisks().stream().filter(risk -> risk.riskType().equals("THIRD_PARTY_DAMAGE")).findFirst();
        assertTrue(optRisk1.isPresent());
        assertEquals(0, new BigDecimal("12.00").compareTo(optRisk1.get().premium()));
        assertEquals(0, new BigDecimal("100.00").compareTo(optRisk1.get().sumInsured()));
        var optRisk2 = actualObject1.getRisks().stream().filter(risk -> risk.riskType().equals("THEFT")).findFirst();
        assertTrue(optRisk2.isPresent());
        assertEquals(0, new BigDecimal("30.00").compareTo(optRisk2.get().premium()));
        assertEquals(0, new BigDecimal("1000.00").compareTo(optRisk2.get().sumInsured()));
        var optRisk3 = actualObject1.getRisks().stream().filter(risk -> risk.riskType().equals("DAMAGE")).findFirst();
        assertTrue(optRisk3.isPresent());
        assertEquals(0, new BigDecimal("6.95").compareTo(optRisk3.get().premium()));
        assertEquals(0, new BigDecimal("500.00").compareTo(optRisk3.get().sumInsured()));
        assertEquals(0, new BigDecimal("48.95").compareTo(actualObject1.getPremium()));
        assertEquals(0, new BigDecimal("5.78").compareTo(actualObject2.getPremium()));
        assertEquals(0, new BigDecimal("54.73").compareTo(actualPremiumResponse.premium()));
    }

    private void setUpPremiumRequest() {
        bicyclePolicy1 = new BicyclePolicy();
        ReflectionTestUtils.setField(bicyclePolicy1, "make", "Pearl");
        ReflectionTestUtils.setField(bicyclePolicy1, "model", "Gravel SL EVO");
        ReflectionTestUtils.setField(bicyclePolicy1, "manufactureYear", 2015);
        ReflectionTestUtils.setField(bicyclePolicy1, "coverage", CoverageType.EXTRA);
        ReflectionTestUtils.setField(bicyclePolicy1, "sumInsured", new BigDecimal("1000"));
        ReflectionTestUtils.setField(bicyclePolicy1, "risks", Set.of("THEFT", "DAMAGE", "THIRD_PARTY_DAMAGE"));
        bicyclePolicy2 = new BicyclePolicy();
        ReflectionTestUtils.setField(bicyclePolicy2, "make", "Sensa");
        ReflectionTestUtils.setField(bicyclePolicy2, "model", "V2");
        ReflectionTestUtils.setField(bicyclePolicy2, "manufactureYear", 2020);
        ReflectionTestUtils.setField(bicyclePolicy2, "coverage", CoverageType.STANDARD);
        ReflectionTestUtils.setField(bicyclePolicy2, "sumInsured", new BigDecimal("225"));
        ReflectionTestUtils.setField(bicyclePolicy2, "risks", Set.of("DAMAGE"));
        premiumRequest = new PremiumRequest(List.of(bicyclePolicy1, bicyclePolicy2));
    }

}