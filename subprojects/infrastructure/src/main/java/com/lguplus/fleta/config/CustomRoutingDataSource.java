package com.lguplus.fleta.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? "reader" : "writer";

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.debug(">>> Reader");
            return "reader";
        } else {
            log.debug(">>> Writer");
            return "writer";
        }
    }

}