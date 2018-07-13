package com.calculate.realtime.statistics.fixtures;

import com.calculate.realtime.statistics.model.StatisticsData;

public class StatisticsFixtures {

    public static final String baseUrl = "http://localhost:8080";

    public static StatisticsData getDefaultStatisticData() {
        Double defaultAmount = 0.0;
        StatisticsData statisticsData = new StatisticsData();
        statisticsData.setSum(defaultAmount);
        statisticsData.setMax(defaultAmount);
        statisticsData.setMin(defaultAmount);
        statisticsData.setCount(0l);
        statisticsData.setAvg(defaultAmount);
        return statisticsData;
    }

    public static StatisticsData getExpectedStatisticData(Double transactionAmount, Long count) {
        StatisticsData expectedStatisticsData = new StatisticsData();
        expectedStatisticsData.setAvg(transactionAmount);
        expectedStatisticsData.setMax(transactionAmount);
        expectedStatisticsData.setMin(transactionAmount);
        expectedStatisticsData.setCount(count);
        expectedStatisticsData.setSum(transactionAmount);
        return expectedStatisticsData;
    }
}
