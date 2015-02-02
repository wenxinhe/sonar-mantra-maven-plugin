package com.thoughtworks.sonar.mantra;

import com.google.common.base.Joiner;
import com.thoughtworks.sonar.mantra.datasource.DataSource;
import com.thoughtworks.sonar.mantra.datasource.DataSourceForCi;
import com.thoughtworks.sonar.mantra.datasource.DataSourceUrl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "check")
public class CheckDeltaMojo extends AbstractMojo {
    @Parameter
    private List<Mantra> mantras = new ArrayList<Mantra>();
    @Parameter(property = "sonar.host.url", defaultValue = "http://localhost:9000", alias = "sonar.host.url")
    private String sonarUrl;
    @Parameter(property = "project.groupId")
    private String groupId;
    @Parameter(property = "project.artifactId")
    private String artifactId;

    private DataSource dataSource;

    public CheckDeltaMojo() throws IOException {
        dataSource = createDataSource();
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        for (Mantra mantra : mantras) {
            CheckResult checkResult = mantra.check(dataSource);
            if (checkResult.isWorse()) {
                throw new RuntimeException(checkResult.getMsg());
            }
        }
    }

    private String createUrlForCi() {
        String queryUrl = sonarUrl + "/api/timemachine/index?fomat=json&resource="
                + groupId + ":" + artifactId;
        queryUrl += "&metrics=";
        List<String> metrics = new ArrayList<String>();
        for (Mantra mantra : mantras) {
            metrics.add(mantra.getMetric());
        }
        return queryUrl + Joiner.on(",").join(metrics);
    }

    private DataSource createDataSource() throws IOException {
        return new DataSourceForCi(new DataSourceUrl(createUrlForCi()).getInputStream());
    }

}
