package com.thoughtworks.sonar.mantra;

import java.util.ArrayList;
import java.util.List;

public class MetricMeasures {
    private List<Double> historyData = new ArrayList<Double>();

    public MetricMeasures(List<Double> historyData) {
        for(int i = historyData.size() - 1; i >=0 ; i--) {
            this.historyData.add(historyData.get(i));
        }
    }

    public List<Double> getHistoryData() {
        return historyData;
    }

    public CheckResult checkMantraBreakness(Mantra mantra) {
        if(historyData.size() == 0) return CheckResult.passResult();

        int index = 0;
        Double currentValue = historyData.get(index);
        if(!mantra.isWorseThanSafeValue(currentValue)) return CheckResult.passResult();

        for(int i = 0;i < mantra.getCompareHistoryCount();i ++) {
            if(++index >= historyData.size()) break;
            CheckResult checkResult = mantra.checkFirstWorseThanSecond(currentValue, historyData.get(index));
            if(checkResult.isWorse()) {
                return checkResult;
            }
        }
        return CheckResult.passResult();
    }
}
