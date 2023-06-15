package lv.proofit.bicycle.engine;

import com.google.common.base.CaseFormat;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.proofit.bicycle.engine.model.*;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class ScriptExecutor {

    private static final String BASE_SCRIPT = "BaseScript.groovy";
    private static final String CALCULATE_METHOD = "calculate";
    @Value("${groovy.scripts.path}")
    private String scriptsPath;
    private GroovyScriptEngine groovyScriptEngine;

    @SneakyThrows
    @PostConstruct
    void postConstruct() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(BASE_SCRIPT);
        groovyScriptEngine = new GroovyScriptEngine(scriptsPath);
        groovyScriptEngine.setConfig(compilerConfiguration);
    }


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

    private Class<?> loadScript(String scriptName) throws ResourceException, ScriptException {
        return groovyScriptEngine.loadScriptByName(scriptName);
    }

}
