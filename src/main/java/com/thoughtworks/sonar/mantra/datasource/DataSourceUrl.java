package com.thoughtworks.sonar.mantra.datasource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
* Created by vincent on 15-2-2.
*/
public class DataSourceUrl {
    private String url;

    public DataSourceUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse execute = httpClient.execute(httpGet);
        return execute.getEntity().getContent();
    }
}
