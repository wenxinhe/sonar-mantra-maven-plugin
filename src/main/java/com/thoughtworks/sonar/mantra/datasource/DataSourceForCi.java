package com.thoughtworks.sonar.mantra.datasource;

import com.thoughtworks.sonar.mantra.MetricsInfoParser;
import com.thoughtworks.sonar.mantra.jsondata.MetricsResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * Created by vincent on 15-1-29.
 */
public class DataSourceForCi implements DataSource {
    private MetricsResponse metricsResponse;

    public DataSourceForCi(InputStream inputStream) {
        metricsResponse = new MetricsInfoParser()
                .getMetricsResponse(new InputStreamReader(inputStream));
    }

    @Override
    public Double getCurrentValue(String metric) {
        List<Double> historyData = getHistoryData(metric);
        if (historyData.size() == 0) {
            throw new IllegalStateException("historyData size == 0");
        }
        return historyData.get(0);
    }

    @Override
    public List<Double> getHistoryValues(String metric) {
        List<Double> historyData = getHistoryData(metric);
        return historyData.size() == 1 ? Collections.<Double>emptyList() :
                historyData.subList(1, historyData.size());
    }

    private List<Double> getHistoryData(String metric) {
        return metricsResponse.getMetricByName(metric).getHistoryData();
    }

}
