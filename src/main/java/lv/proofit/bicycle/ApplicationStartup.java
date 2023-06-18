package lv.proofit.bicycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
@Slf4j
@Profile("!test")
public class ApplicationStartup {

    @Value("${groovy.scripts.path}")
    private String groovyScriptsPath;

    @Autowired
    private ApplicationContext applicationContext;

    public void shutdown(int returnCode){
        SpringApplication.exit(applicationContext, () -> returnCode);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        log.info("Checking if groovy scripts exists...");
        var dir = new File(groovyScriptsPath);
        var count = Optional.ofNullable(dir.list())
                .map(Arrays::asList)
                .orElse(Collections.emptyList()).stream().filter(fileName -> fileName.endsWith(".groovy")).count();
        if (count > 0) {
            log.info("Found {} groovy scripts in {}", count, groovyScriptsPath);
        } else {
            log.error("Scripts in {} not found! Application will be shutdown!\n\n \t\t Please provide correct groovy.scripts.path property value! \n", groovyScriptsPath);
            shutdown(0);
        }
    }


}
