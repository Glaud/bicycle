package lv.proofit.bicycle;

import groovy.util.GroovyScriptEngine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppConfig {

    @Value("${groovy.base-script}")
    private String baseScript;
    @Value("${groovy.scripts.path}")
    private String scriptsPath;

    @SneakyThrows
    @Bean
    public GroovyScriptEngine groovyScriptEngine() {
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setScriptBaseClass(baseScript);
        var groovyScriptEngine = new GroovyScriptEngine(scriptsPath);
        log.info("Created GroovyScriptEngine from scripts found in {}", scriptsPath);
        groovyScriptEngine.setConfig(compilerConfiguration);
        return groovyScriptEngine;
    }


}
