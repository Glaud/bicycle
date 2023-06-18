import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SumInsuredDamage extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("SumInsuredDamage");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating SumInsuredDamage for ${calcRequest}"
        def sumInsured = calcRequest.sumInsured * 0.5
        logger.info "sumInsured=${sumInsured}"
        return sumInsured
    }
}