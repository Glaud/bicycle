class PremiumDamage extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating PremiumDamage for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        println "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        println "sumInsuredFactor=${sumInsuredFactor}"
        def bicycleAgeFactor = super.findBicycleAgeFactor(calcRequest)
        println "bicycleAgeFactor=${bicycleAgeFactor}"
        def premium = riskBasePremium * sumInsuredFactor * bicycleAgeFactor;
        println "premium=${premium}"
        return premium
    }
}