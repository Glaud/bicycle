package lv.proofit.bicycle.engine.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class CalculationRequest {
    private String make;
    private String model;
    private Integer year;
    private String risk;
    private int count;
    private BigDecimal sumInsured;
}