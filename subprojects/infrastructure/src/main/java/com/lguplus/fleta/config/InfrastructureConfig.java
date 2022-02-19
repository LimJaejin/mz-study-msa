package com.lguplus.fleta.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "com.lguplus.fleta")
@EnableFeignClients(basePackages = "com.lguplus.fleta")
public class InfrastructureConfig {

    private static final String WDS = "writeDataSource";
    private static final String RDS = "readDataSource";

    private final HikariConfigProperties hikariConfigProperties;

    @Bean(WDS)
    public DataSource writeDataSource() {
        log.debug(">>> hikariConfigProperties: {}", this.hikariConfigProperties.toString());
        return this.hikariConfigProperties.getWriteDataSource();
    }

    @Bean(RDS)
    public DataSource readDataSource() {
        return this.hikariConfigProperties.getReadDataSource();
    }

    @Bean
    @DependsOn({WDS, RDS})
    public DataSource routingDataSource(@Qualifier(WDS) DataSource writeDataSource, @Qualifier(RDS) DataSource readDataSource) {
        Map<Object, Object> datasourceMap = new HashMap<>();
        datasourceMap.put("write", writeDataSource);
        datasourceMap.put("read", readDataSource);

        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Primary
    @Bean
    @DependsOn("routingDataSource")
    public DataSource lazyConnectionDataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    @DependsOn("lazyConnectionDataSource")
    public PlatformTransactionManager transactionManager(@Qualifier("lazyConnectionDataSource") DataSource lazyConnectionDataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(lazyConnectionDataSource);
        return transactionManager;
    }

}
