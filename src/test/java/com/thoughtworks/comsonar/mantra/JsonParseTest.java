package com.thoughtworks.comsonar.mantra;

import com.google.common.collect.Lists;
import com.thoughtworks.sonar.mantra.MetricMeasures;
import com.thoughtworks.sonar.mantra.MetricsInfoParser;
import com.thoughtworks.sonar.mantra.jsondata.MetricsResponse;
import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class JsonParseTest {
    @Test
    public void should_parse_json_correctly() {
        String toParse = "            [{\n" +
                "        cols: [\n" +
                "        {\n" +
                "            metric: \"coverage\"\n" +
                "        },\n" +
                "        {\n" +
                "            metric: \"violations_density\"\n" +
                "        }\n" +
                "        ],\n" +
                "        cells: [\n" +
                "        {\n" +
                "            d: \"2014-11-11T22:37:30+0800\",\n" +
                "                    v: [\n" +
                "            74,\n" +
                "                    75.3\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            d: \"2014-11-12T07:22:07+0800\",\n" +
                "                    v: [\n" +
                "            74,\n" +
                "                    75.3\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            d: \"2014-11-14T15:42:53+0800\",\n" +
                "                    v: [\n" +
                "            39.8,\n" +
                "                    72.6\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            d: \"2014-11-16T14:27:15+0800\",\n" +
                "                    v: [\n" +
                "            39.8,\n" +
                "                    72.6\n" +
                "            ]\n" +
                "        }\n" +
                "        ]\n" +
                "    }]";
        StringReader stringReader = new StringReader(toParse);
        MetricsResponse metricsResponse = new MetricsInfoParser()
                                          .getMetricsResponse(stringReader);

        assertEquals(74, metricsResponse.getCells().get(0).getV().get(0), 0.01);
        assertEquals(75.3, metricsResponse.getCells().get(0).getV().get(1), 0.01);
        assertEquals(11, metricsResponse.getCells().get(0).getD().getDayOfMonth());

        MetricMeasures coverage = metricsResponse.getMetricByName("coverage");
        assertEquals(74, coverage.getHistoryData().get(3), 0.01);
        assertEquals(39.8, coverage.getHistoryData().get(1), 0.01);
    }

}
