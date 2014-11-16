package com.thoughtworks.sonar.mantra;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.sonar.mantra.jsondata.MetricsResponse;
import org.joda.time.DateTime;

import java.io.Reader;

public class MetricsInfoParser {
    public MetricsResponse getMetricsResponse(Reader reader) {
        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                        .create();
        return gson.fromJson(reader, MetricsResponse[].class)[0];
    }
}
