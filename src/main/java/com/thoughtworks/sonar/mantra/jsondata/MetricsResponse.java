package com.thoughtworks.sonar.mantra.jsondata;

import com.thoughtworks.sonar.mantra.MetricMeasures;

import java.util.ArrayList;
import java.util.List;

public class MetricsResponse {
    private List<Metric> cols;
    private List<Measure> cells;

    public List<Measure> getCells() {
        return cells;
    }

    public MetricMeasures getMetricByName(String metricName) {
        int metricIndex = 0;
        for(int i = 0;i < cols.size();i++)
            if(cols.get(i).getMetric().equals(metricName)) {
                metricIndex = i;
                break;
            }
        List<Double> historyData = new ArrayList<Double>();
        for (Measure cell : cells) {
            historyData.add(cell.getV().get(metricIndex));
        }
        return new MetricMeasures(historyData);
    }
}
