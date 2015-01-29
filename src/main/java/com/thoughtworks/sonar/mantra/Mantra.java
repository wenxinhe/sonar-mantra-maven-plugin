package com.thoughtworks.sonar.mantra;

import com.thoughtworks.sonar.mantra.datasource.DataSource;

import java.util.List;

public class Mantra {
    private String metric;
    private String shouldBe;
    private Integer compareHistoryCount;
    private Double safeValue;

    public Integer getCompareHistoryCount() {
        return compareHistoryCount;
    }

    public Mantra(String metric, String shouldBe, Integer compareHistoryCount, Double safeValue) {
        this.metric = metric;
        this.shouldBe = shouldBe;
        this.compareHistoryCount = compareHistoryCount;
        this.safeValue = safeValue;
    }

    public String getMetric() {
        return metric;
    }

    public CheckResult checkFirstWorseThanSecond(Double first, Double second) {
        if (Compare.withComparer(shouldBe).firstIsWorseThanSecond(first, second))
            return CheckResult.failResult("Mantra check fail, the current value of " + metric +
                    " is " + first + ", which is worse than the values from previous " + compareHistoryCount +
                    " analysis as you configured");
        return CheckResult.passResult();
    }

    public boolean isWorseThanSafeValue(Double currentValue) {
        return checkFirstWorseThanSecond(currentValue, safeValue).isWorse();
    }

    public CheckResult check(DataSource dataSource) {
        return check(dataSource.getCurrentValue(this.metric), dataSource.getHistoryValues(this.metric));
    }

    public CheckResult check(Double currentValue, List<Double> historyValues) {
        if (historyValues.size() == 0)
            return CheckResult.passResult();

        if (!isWorseThanSafeValue(currentValue))
            return CheckResult.passResult();

        for (int i = 0; i < getCompareHistoryCount(); i++) {
            CheckResult checkResult = checkFirstWorseThanSecond(currentValue, historyValues.get(i));
            if (checkResult.isWorse()) {
                return checkResult;
            }
        }
        return CheckResult.passResult();
    }
}
