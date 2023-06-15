import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PremiumTheft extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("PremiumTheft");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating PremiumTheft for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        logger.info "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        logger.info "sumInsuredFactor=${sumInsuredFactor}"
        def premium = riskBasePremium * sumInsuredFactor;
        logger.info "premium=${premium}"
        return premium
    }
}