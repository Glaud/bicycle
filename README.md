# Bicycle policy premium
## Calculating premiums for bicycle policies

This is Spring Boot application that calculates sum insured and premium for risks for the bicycle policy. Business logic for calculations resides in groovy scripts. In order to add new risk or modify existing one there is no need for application restart - just script addition or update. Script's name should be created as:
(Premium|SumInsured)<RiskName> - e.g. _PremiumDamage, PremiumTheft, SumInsuredDamage, SumInsuredTheft_

Each script should extend _BaseScript_ and implement _PremiumCalculator_ interface overriding

```BigDecimal calculate(def calcRequest)```

method.

## Launch application
You can start application by running command 

``` mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dgroovy.scripts.path=/home/projects/proof-it/groovy" ```

where _groovy.scripts.path_ is groovy scripts path. Default scripts you can find here https://github.com/Glaud/bicycle/tree/master/src/main/groovy. You need to provide correct path with groovy scripts inside in order to properly start application.

### Swagger
Swagger is exposed at http://localhost:8080/bicycle/swagger-ui/

## What's missing
- write tests in ScriptExecutorIT for throwing exceptions
- handle boundary conditions in groovy scripts
- write test for REST layer
- load default groovy scripts if scripts cannot be find in specified path
