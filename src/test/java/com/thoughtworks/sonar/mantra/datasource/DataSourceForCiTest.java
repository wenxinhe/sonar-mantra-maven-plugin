package com.thoughtworks.sonar.mantra.datasource;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

public class DataSourceForCiTest {


    private final InputStream inputStream = new ByteArrayInputStream(("            [{\n" +
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
            "    }]").getBytes());
    private DataSourceForCi dataSourceForCi;

    @Test
    public void should_be_able_to_getCurrentValue() throws Exception {
        dataSourceForCi = new DataSourceForCi(inputStream);

        assertThat(dataSourceForCi.getCurrentValue("coverage"), equalTo(39.8));
        assertThat(dataSourceForCi.getCurrentValue("violations_density"), equalTo(72.6));
    }

    @Test
    public void should_be_able_to_getHistoryValues() throws Exception {
        dataSourceForCi = new DataSourceForCi(inputStream);

        {
            List<Double> historyValues = dataSourceForCi.getHistoryValues("coverage");
            assertThat(historyValues, hasSize(3));
            assertThat(historyValues, hasItems(39.8d, 74d, 74d));
        }
        {
            List<Double> historyValues = dataSourceForCi.getHistoryValues("violations_density");
            assertThat(historyValues, hasSize(3));
            assertThat(historyValues, hasItems(72.6d, 75.3d, 75.3d));
        }
    }
}