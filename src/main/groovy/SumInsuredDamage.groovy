class SumInsuredDamage extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating SumInsuredDamage for ${calcRequest}"
        def sumInsured = calcRequest.sumInsured * 0.5
        println "sumInsured=${sumInsured}"
        return sumInsured
    }
}