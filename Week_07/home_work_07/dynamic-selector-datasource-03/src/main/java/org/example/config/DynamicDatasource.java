package org.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @desription 数据源路由配置
 * @author 孟令超
 * @date 2020/12/3
 * @since jdk1.8
 * @version 1.0
 */
public class DynamicDatasource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDatasourceHolder.getDetermineCurrentLookupKey();
    }
}
