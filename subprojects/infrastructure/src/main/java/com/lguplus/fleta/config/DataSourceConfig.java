package com.lguplus.fleta.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableTransactionManagement
@Configuration
public class DataSourceConfig {

    private static final String WDS = "writerDataSource";
    private static final String RDS = "readerDataSource";

    private final HikariConfigProperties hikariConfigProperties;

    @Bean(WDS)
    public DataSource writerDataSource() {
        log.debug(">>> hikariConfigProperties: {}", this.hikariConfigProperties.toString());
        return this.hikariConfigProperties.getWriterDataSource();
    }

    @Bean(RDS)
    public DataSource readerDataSource() {
        return this.hikariConfigProperties.getReaderDataSource();
    }

    @Bean
    @Primary
    @DependsOn({WDS, RDS})
    public DataSource routingDataSource(
        @Qualifier(WDS) DataSource writerDataSource,
        @Qualifier(RDS) DataSource readerDataSource
    ) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("writer", writerDataSource);
        dataSourceMap.put("reader", readerDataSource);

        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writerDataSource);

        return routingDataSource;
    }

    @Bean
    @DependsOn("routingDataSource")
    public LazyConnectionDataSourceProxy lazyConnectionDataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(
        @Qualifier("lazyConnectionDataSource") DataSource lazyConnectionDataSource
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(lazyConnectionDataSource);
        return transactionManager;
    }
}
