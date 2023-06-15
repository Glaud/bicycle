class SumInsuredThirdPartyDamage extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating SumInsuredThirdPartyDamage for ${calcRequest}"
        def sumInsured = 100
        println "sumInsured=${sumInsured}"
        return sumInsured
    }
}