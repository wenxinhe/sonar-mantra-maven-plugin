package com.thoughtworks.sonar.mantra.datasource;

import java.io.IOException;

/**
 * Created by vincent on 15-1-29.
 */
public class DataSourceFactory {
    public static DataSource createDataSource(boolean isLocalRun, String urlForCi, String urlForLocal) throws IOException {
        return new DataSourceForCi(urlForCi);
    }
}
