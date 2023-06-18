import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SumInsuredTheft extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("SumInsuredTheft");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating SumInsuredTheft for ${calcRequest}"
        def sumInsured = calcRequest.sumInsured
        logger.info "sumInsured=${sumInsured}"
        return sumInsured
    }
}