import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SumInsuredThirdPartyDamage extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("PremiumDamage");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating SumInsuredThirdPartyDamage for ${calcRequest}"
        def sumInsured = 100
        logger.info "sumInsured=${sumInsured}"
        return sumInsured
    }
}