import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PremiumDamage extends BaseScript implements PremiumCalculator {

    final Logger logger = LoggerFactory.getLogger("PremiumDamage");

    BigDecimal calculate(def calcRequest) {
        logger.info "Calculating PremiumDamage for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        logger.info "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        logger.info "sumInsuredFactor=${sumInsuredFactor}"
        def bicycleAgeFactor = super.findBicycleAgeFactor(calcRequest)
        logger.info "bicycleAgeFactor=${bicycleAgeFactor}"
        def premium = riskBasePremium * sumInsuredFactor * bicycleAgeFactor;
        logger.info "premium=${premium}"
        return premium
    }
}