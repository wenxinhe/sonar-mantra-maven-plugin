package com.thoughtworks.sonar.mantra.datasource;

import java.util.List;

/**
 * Created by vincent on 15-1-29.
 */
public interface DataSource {
    Double getCurrentValue(String metric);

    List<Double> getHistoryValues(String metric);
}
