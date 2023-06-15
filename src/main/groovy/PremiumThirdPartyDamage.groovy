class PremiumThirdPartyDamage extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating PremiumThirdPartyDamage for ${calcRequest}"
        def riskBasePremium = super.findRiskBasePremiumFactor(calcRequest.risk)
        println "riskBasePremium=${riskBasePremium}"
        def sumInsuredFactor = super.findSumInsuredFactor(calcRequest)
        println "sumInsuredFactor=${sumInsuredFactor}"
        def riskCountFactor = super.findRiskCountFactor(calcRequest.count)
        println "riskCountFactor=${riskCountFactor}"
        def premium = riskBasePremium * sumInsuredFactor * riskCountFactor;
        println "premium=${premium}"
        return premium
    }
}