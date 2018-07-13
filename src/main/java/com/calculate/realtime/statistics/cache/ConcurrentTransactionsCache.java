package com.calculate.realtime.statistics.cache;

import com.calculate.realtime.statistics.config.CommonConstants;
import com.calculate.realtime.statistics.model.StatisticsInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConcurrentTransactionsCache {

    private final Map<Integer, StatisticsInfo> data
            = new ConcurrentHashMap<>(CommonConstants.REQUIRED_INTERVAL_IN_SECONDS);

    public Map<Integer, StatisticsInfo> getData() {
        return data;
    }
}
