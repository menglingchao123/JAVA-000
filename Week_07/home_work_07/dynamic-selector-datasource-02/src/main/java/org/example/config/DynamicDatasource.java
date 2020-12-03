package org.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDatasource extends AbstractRoutingDataSource {

    /**
     * 通过此方法指定数据key
     * @return
     */
    protected Object determineCurrentLookupKey() {
        return DynamicDatasourceHolder.getDetermineCurrentLookupKey();
    }

}
