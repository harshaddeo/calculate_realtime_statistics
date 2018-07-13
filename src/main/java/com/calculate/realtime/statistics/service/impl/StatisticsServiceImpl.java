package com.calculate.realtime.statistics.service.impl;

import com.calculate.realtime.statistics.cache.ConcurrentTransactionsCache;
import com.calculate.realtime.statistics.config.CommonConstants;
import com.calculate.realtime.statistics.model.StatisticsInfo;
import com.calculate.realtime.statistics.model.StatisticsData;
import com.calculate.realtime.statistics.model.Transaction;
import com.calculate.realtime.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final ConcurrentTransactionsCache concurrentTransactionsCache;

    @Autowired
    public StatisticsServiceImpl(ConcurrentTransactionsCache concurrentTransactionsCache) {
        this.concurrentTransactionsCache = concurrentTransactionsCache;
    }

    @Override
    public StatisticsData get() {
        long currentTime = System.currentTimeMillis();
        StatisticsData statsData = concurrentTransactionsCache.getData().values()
                .stream()
                .filter(s -> (currentTime - s.getTimestamp()) / 1000 < CommonConstants.REQUIRED_INTERVAL_IN_SECONDS)
                .map(StatisticsData::new)
                .reduce(new StatisticsData(), (s1, s2) -> {
                    s1.setSum(s1.getSum() + s2.getSum());
                    s1.setCount(s1.getCount() + s2.getCount());
                    s1.setMax(Double.compare(s1.getMax(), s2.getMax()) > 0 ? s1.getMax() : s2.getMax());
                    s1.setMin(Double.compare(s1.getMin(), s2.getMin()) < 0 ? s1.getMin() : s2.getMin());
                    return s1;
                });
        updateStatisticsData(statsData);
        return statsData;
    }


    @Override
    public void calculate(Transaction transaction, Long currentTimestamp) {

        int second = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneId.systemDefault())
                .getSecond();

        concurrentTransactionsCache.getData().compute(second, (key, existingStatistic) -> {
            if (Objects.isNull(existingStatistic) || isStatisticsUnderInterestedInterval(currentTimestamp, existingStatistic)) {
                return createStatistics(transaction);
            }
            return updateStatistics(transaction, existingStatistic);
        });
    }

    private boolean isStatisticsUnderInterestedInterval(Long currentTimestamp, StatisticsInfo existingStatistic) {
        return (currentTimestamp - existingStatistic.getTimestamp()) / 1000 >= CommonConstants.REQUIRED_INTERVAL_IN_SECONDS;
    }

    private StatisticsInfo updateStatistics(Transaction transaction, StatisticsInfo existingStatistic) {
        existingStatistic.setCount(existingStatistic.getCount() + 1);
        existingStatistic.setSum(existingStatistic.getSum() + transaction.getAmount());
        if (Double.compare(transaction.getAmount(), existingStatistic.getMax()) > 0) {
            existingStatistic.setMax(transaction.getAmount());
        }
        if (Double.compare(transaction.getAmount(), existingStatistic.getMin()) < 0) {
            existingStatistic.setMin(transaction.getAmount());
        }
        return existingStatistic;
    }

    private StatisticsInfo createStatistics(Transaction transaction) {
        StatisticsInfo statistics = new StatisticsInfo();
        statistics.setTimestamp(transaction.getTimestamp());
        statistics.setSum(transaction.getAmount());
        statistics.setMax(transaction.getAmount());
        statistics.setMin(transaction.getAmount());
        statistics.setCount(1L);
        return statistics;
    }

    private void updateStatisticsData(StatisticsData data) {
        data.setAvg(calculateAverage(data));
        data.setMin(Double.compare(data.getMin(), Double.MAX_VALUE) == 0 ? 0.00 : data.getMin());
        data.setMax(Double.compare(data.getMax(), Double.MIN_VALUE) == 0 ? 0.00 : data.getMax());
    }

    private Double calculateAverage(StatisticsData data) {
        Double average = data.getCount() > 0L ? (data.getSum() / data.getCount()) : 0.00;
        return Double.valueOf(CommonConstants.DECIMAL_FORMAT.format(average));
    }
}
