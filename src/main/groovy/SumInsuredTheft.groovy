class SumInsuredTheft extends BaseScript implements PremiumCalculator {

    BigDecimal calculate(def calcRequest) {
        println "Calculating SumInsuredTheft for ${calcRequest}"
        def sumInsured = calcRequest.sumInsured
        println "sumInsured=${sumInsured}"
        return sumInsured
    }
}