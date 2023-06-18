import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.Year

class BaseScript extends Script {

    final Logger logger = LoggerFactory.getLogger("BaseScript");

    @Override
    Object run() {
        return null
    }

    ArrayList<LinkedHashMap<String, Serializable>> getAgeFactorData() {
        return [['MAKE': 'Canyon', 'MODEL': 'CF 5', 'VALUE_FROM': 0, 'VALUE_TO': 5, 'FACTOR_MIN': 1.5, 'FACTOR_MAX': 2],
                ['MAKE': 'Canyon', 'MODEL': 'CF 5', 'VALUE_FROM': 6, 'VALUE_TO': 10, 'FACTOR_MIN': 1.2, 'FACTOR_MAX': 1.4],
                ['MAKE': 'Canyon', 'MODEL': 'CF 5', 'VALUE_FROM': 11, 'VALUE_TO': 15, 'FACTOR_MIN': 0.9, 'FACTOR_MAX': 1.1],
                ['MAKE': 'Canyon', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 0.95, 'FACTOR_MAX': 1.6],
                ['MAKE': 'Whyte', 'MODEL': 'T-160 RS', 'VALUE_FROM': 0, 'VALUE_TO': 4, 'FACTOR_MIN': 1.6, 'FACTOR_MAX': 2.05],
                ['MAKE': 'Whyte', 'MODEL': 'T-160 RS', 'VALUE_FROM': 5, 'VALUE_TO': 10, 'FACTOR_MIN': 1.2, 'FACTOR_MAX': 1.5],
                ['MAKE': 'Whyte', 'MODEL': 'T-160 RS', 'VALUE_FROM': 11, 'VALUE_TO': 15, 'FACTOR_MIN': 0.9, 'FACTOR_MAX': 1.1],
                ['MAKE': 'Whyte', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 0.95, 'FACTOR_MAX': 1.6],
                ['MAKE': 'Pearl', 'MODEL': 'Gravel SL EVO', 'VALUE_FROM': 0, 'VALUE_TO': 2, 'FACTOR_MIN': 2.1, 'FACTOR_MAX': 2.5],
                ['MAKE': 'Pearl', 'MODEL': 'Gravel SL EVO', 'VALUE_FROM': 3, 'VALUE_TO': 6, 'FACTOR_MIN': 1.5, 'FACTOR_MAX': 2],
                ['MAKE': 'Pearl', 'MODEL': 'Gravel SL EVO', 'VALUE_FROM': 7, 'VALUE_TO': 15, 'FACTOR_MIN': 0.9, 'FACTOR_MAX': 1.4],
                ['MAKE': 'Pearl', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 0.99, 'FACTOR_MAX': 1.8],
                ['MAKE': 'Krush', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 0.93, 'FACTOR_MAX': 1.75],
                ['MAKE': 'Megamo', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 1.1, 'FACTOR_MAX': 2.3],
                ['MAKE': 'Sensa', 'VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 0.8, 'FACTOR_MAX': 2.5],
                ['VALUE_FROM': 0, 'VALUE_TO': 15, 'FACTOR_MIN': 1, 'FACTOR_MAX': 3]]
    }

    ArrayList<LinkedHashMap<String, Serializable>> getRiskCountFactorData() {
        return [['VALUE_FROM': 0, 'VALUE_TO': 1, 'FACTOR_MIN': 1.3, 'FACTOR_MAX': 1.3],
                ['VALUE_FROM': 2, 'VALUE_TO': 3, 'FACTOR_MIN': 1.2, 'FACTOR_MAX': 1.2],
                ['VALUE_FROM': 3, 'VALUE_TO': 4, 'FACTOR_MIN': 1.1, 'FACTOR_MAX': 1.1],
                ['VALUE_FROM': 5, 'VALUE_TO': 10, 'FACTOR_MIN': 1, 'FACTOR_MAX': 1]]
    }

    ArrayList<LinkedHashMap<String, Serializable>> getSumInsuredFactorData() {
        return [['VALUE_FROM': 100, 'VALUE_TO': 1000, 'FACTOR_MIN': 0.5, 'FACTOR_MAX': 1],
                ['VALUE_FROM': 1001, 'VALUE_TO': 3000, 'FACTOR_MIN': 1, 'FACTOR_MAX': 2],
                ['VALUE_FROM': 3001, 'VALUE_TO': 5000, 'FACTOR_MIN': 2, 'FACTOR_MAX': 3],
                ['VALUE_FROM': 3001, 'VALUE_TO': 5000, 'FACTOR_MIN': 2, 'FACTOR_MAX': 3],

        ]
    }

    ArrayList<LinkedHashMap<String, Serializable>> getRiskBasePremiumData() {
        return [['RISK_TYPE': 'DAMAGE', 'PREMIUM': 10],
                ['RISK_TYPE': 'THIRD_PARTY_DAMAGE', 'PREMIUM': 20],
                ['RISK_TYPE': 'THEFT', 'PREMIUM': 30]]
    }


    LinkedHashMap<String, Serializable> findAgeFactorMap(String make, String model, Integer age) {
        def ageFactorMap = getAgeFactorData()
                .findAll {
                    it.MAKE == make && it.MODEL == model && age >= (it.VALUE_FROM as Integer) && age <= (it.VALUE_TO as Integer)
                }
        if (!ageFactorMap) {
            ageFactorMap = getAgeFactorData()
                    .findAll {
                        it.MAKE == make && !it.containsKey("MODEL") && age >= (it.VALUE_FROM as Integer) && age <= (it.VALUE_TO as Integer)
                    }
            if (!ageFactorMap) {
                ageFactorMap = getAgeFactorData()
                        .findAll {
                            !it.containsKey("MAKE") && !it.containsKey("MODEL") && age >= (it.VALUE_FROM as Integer) && age <= (it.VALUE_TO as Integer)
                        }
            }
        }
        return !ageFactorMap ? null : ageFactorMap[0];

    }

    BigDecimal findBicycleAgeFactor(Object calcRequest) {
        def age = Year.now().getValue() - calcRequest.year
        def map = findAgeFactorMap(calcRequest.make, calcRequest.model, age)
        return calculateFactor(map, age)
    }

    BigDecimal findRiskCountFactor(Integer insuredObjectsCount) {
        def data = getRiskCountFactorData()
                .findAll { insuredObjectsCount >= (it.VALUE_FROM as Integer) && insuredObjectsCount <= (it.VALUE_TO as Integer) }
        return data ? calculateFactor(data[0], insuredObjectsCount) : null
    }

    BigDecimal findSumInsuredFactor(Object calcRequest) {
        def riskSumInsured = calcRequest.riskSumInsured as BigDecimal
        def sumInsuredFactor = getSumInsuredFactorData()
                .findAll { riskSumInsured.compareTo(it.VALUE_FROM as BigDecimal) >= 0 && riskSumInsured.compareTo(it.VALUE_TO as BigDecimal) <= 0 }
        if (sumInsuredFactor) {
            def map = sumInsuredFactor[0];
            return calculateFactor(map, calcRequest.riskSumInsured)
        } else {
            logger.warn "SumInsuredFactor not found - returning 0 instead"
            return 0
        }
    }

    Integer findRiskBasePremiumFactor(String riskType) {
        def risk = getRiskBasePremiumData().findAll { it.RISK_TYPE == riskType }
        if (risk) {
            return risk[0].PREMIUM as Integer
        } else {
            return null
        }
    }

    BigDecimal calculateFactor(LinkedHashMap<String, Serializable> map, Number value) {
        return (map.FACTOR_MAX as BigDecimal) - ((map.FACTOR_MAX as BigDecimal) - (map.FACTOR_MIN as BigDecimal)) *
                ((map.VALUE_TO as Integer) - value) / ((map.VALUE_TO as Integer) - (map.VALUE_FROM as Integer))
    }
}
