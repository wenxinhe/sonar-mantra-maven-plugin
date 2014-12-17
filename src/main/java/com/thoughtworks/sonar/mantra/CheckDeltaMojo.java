package com.thoughtworks.sonar.mantra;

import com.google.common.base.Joiner;
import com.thoughtworks.sonar.mantra.MetricsInfoParser;
import com.thoughtworks.sonar.mantra.jsondata.MetricsResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Mojo( name = "check")
public class CheckDeltaMojo extends AbstractMojo{
    @Parameter
    private List<Mantra> mantras = new ArrayList<Mantra>();
    @Parameter( property = "sonar.host.url", defaultValue = "http://localhost:9000", alias = "sonar.host.url" )
    private String sonarUrl;
    @Parameter(property = "project.groupId")
    private String groupId;
    @Parameter(property = "project.artifactId")
    private String artifactId;
    private MetricsInfoParser metricsInfoParser = new MetricsInfoParser();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(createUrl());
        try {
            HttpResponse execute = httpClient.execute(httpGet);
            InputStream content = execute.getEntity().getContent();
            MetricsResponse metricsResponse = metricsInfoParser
                    .getMetricsResponse(new InputStreamReader(content));
            checkMetrics(metricsResponse);
            System.out.println("Success!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkMetrics(MetricsResponse rsp) {
        for (Mantra mantra : mantras) {
            CheckResult checkResult = rsp.getMetricByName(mantra.getMetric()).checkMantraBreakness(mantra);
            if(checkResult.isWorse()) {
                throw new RuntimeException(checkResult.getMsg());
            }
        }
    }

    private String createUrl() {
        String queryUrl = sonarUrl + "/api/timemachine/index?fomat=json&resource="
                               + groupId + ":" + artifactId;
        queryUrl += "&metrics=";
        List<String> metrics = new ArrayList<String>();
        for (Mantra mantra : mantras) {
            metrics.add(mantra.getMetric());
        }
        return queryUrl + Joiner.on(",").join(metrics);
    }
}
