package com.thoughtworks.sonar.mantra.datasource;

import com.thoughtworks.sonar.mantra.MetricsInfoParser;
import com.thoughtworks.sonar.mantra.jsondata.MetricsResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * Created by vincent on 15-1-29.
 */
public class DataSourceForCi implements DataSource {
    private MetricsResponse metricsResponse;

    public DataSourceForCi(String url) throws IOException {
        metricsResponse = new MetricsInfoParser()
                .getMetricsResponse(new InputStreamReader(getInputStream(url)));
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

    private InputStream getInputStream(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse execute = httpClient.execute(httpGet);
        return execute.getEntity().getContent();
    }

    private List<Double> getHistoryData(String metric) {
        return metricsResponse.getMetricByName(metric).getHistoryData();
    }
}
