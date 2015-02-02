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
        if (getHistoryData(metric).size() == 0) {
            throw new IllegalStateException("historyData size == 0");
        }
        return getHistoryData(metric).get(0);
    }

    @Override
    public List<Double> getHistoryValues(String metric) {
        return getHistoryData(metric).size() == 1 ? Collections.<Double>emptyList() :
                getHistoryData(metric).subList(1, getHistoryData(metric).size() - 1);
    }

    private List<Double> getHistoryData(String metric) {
        return metricsResponse.getMetricByName(metric).getHistoryData();
    }

}
