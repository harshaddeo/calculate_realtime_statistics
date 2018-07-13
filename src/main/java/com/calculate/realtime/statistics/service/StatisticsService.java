package com.calculate.realtime.statistics.service;

import com.calculate.realtime.statistics.model.StatisticsData;
import com.calculate.realtime.statistics.model.Transaction;

public interface StatisticsService {

    StatisticsData get();

    void calculate(Transaction transaction, Long currentTimestamp);
}
