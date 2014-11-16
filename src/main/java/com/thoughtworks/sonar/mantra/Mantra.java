package com.thoughtworks.sonar.mantra;

public class Mantra{
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

    public Mantra() {
    }

    public String getMetric() {
        return metric;
    }

    public CheckResult checkFirstWorseThanSecond(Double first, Double second) {
        if(Compare.withComparer(shouldBe).firstIsWorseThanSecond(first, second))
            return CheckResult.failResult("Mantra check fail, the current value of " + metric +
                    " is " + first + ", which is worse than the values from previous " + compareHistoryCount +
                    " analysis as you configured");
        return CheckResult.passResult();
    }

    public boolean isWorseThanSafeValue(Double currentValue) {
        return checkFirstWorseThanSecond(currentValue, safeValue).isWorse();
    }
}
