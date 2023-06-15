import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PremiumThirdPartyDamage extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("PremiumThirdPartyDamage");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating PremiumThirdPartyDamage for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        logger.info "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        logger.info "sumInsuredFactor=${sumInsuredFactor}"
        def riskCountFactor = super.findRiskCountFactor(calcRequest.count)
        logger.info "riskCountFactor=${riskCountFactor}"
        def premium = riskBasePremium * sumInsuredFactor * riskCountFactor;
        logger.info "premium=${premium}"
        return premium
    }
}