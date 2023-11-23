package uz.pdp.govqueue.enums;

import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;


@Getter
public enum PeriodEnum {
    DAILY(ChronoUnit.DAYS, 1),
    WEEKLY(ChronoUnit.WEEKS, 1),
    MONTHLY(ChronoUnit.MONTHS, 1),
    QUARTERLY(ChronoUnit.MONTHS, 3),
    YEARLY(ChronoUnit.YEARS, 1),
    CUSTOM(null, 0);

    private final TemporalUnit unit;
    private final int value;

    PeriodEnum(TemporalUnit unit, int value) {
        this.unit = unit;
        this.value = value;
    }
}
