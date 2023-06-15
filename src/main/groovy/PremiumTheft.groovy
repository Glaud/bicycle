class PremiumTheft extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating PremiumTheft for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        println "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        println "sumInsuredFactor=${sumInsuredFactor}"
        def premium = riskBasePremium * sumInsuredFactor;
        println "premium=${premium}"
        return premium
    }
}