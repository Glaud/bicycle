package lv.proofit.bicycle.engine;

import com.google.common.base.CaseFormat;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.proofit.bicycle.engine.model.*;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class ScriptExecutor {

    private static final String BASE_SCRIPT = "BaseScript.groovy";
    private static final String SCRIPTS_PATH = "src/main/groovy";
    private static final String CALCULATE_METHOD = "calculate";

    // TO DO exception handling
    @SneakyThrows
    public BigDecimal calculate(CalculationRequest calculationRequest, CalculationType calculationType) {
        log.info("Start calculating {} for {}", calculationType, calculationRequest);
        var scriptName = createScriptName(calculationRequest, calculationType);
        var scriptClass = loadScript(scriptName);
        var scriptInstance = scriptClass.getDeclaredConstructor().newInstance();
        var result = scriptClass.getDeclaredMethod(CALCULATE_METHOD, Object.class).invoke(scriptInstance, calculationRequest);
        log.info("Calculated {} is {}", calculationType, result);
        return ((BigDecimal) result).setScale(2, RoundingMode.HALF_UP);
    }

    private String createScriptName(CalculationRequest calculationRequest, CalculationType calculationType) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, (calculationType.name() + "_" + calculationRequest.getRisk()).toUpperCase()) + ".groovy";
    }

    private Class<?> loadScript(String scriptName) throws IOException, ResourceException, ScriptException {
        var compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(BASE_SCRIPT);
        var groovyScriptEngine = new GroovyScriptEngine(SCRIPTS_PATH);
        groovyScriptEngine.setConfig(compilerConfiguration);
        return groovyScriptEngine.loadScriptByName(scriptName);
    }

}
