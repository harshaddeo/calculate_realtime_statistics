package com.calculate.realtime.statistics.service.impl;

import com.calculate.realtime.statistics.cache.ConcurrentTransactionsCache;
import com.calculate.realtime.statistics.model.StatisticsData;
import com.calculate.realtime.statistics.model.Transaction;
import com.calculate.realtime.statistics.fixtures.StatisticsFixtures;
import com.calculate.realtime.statistics.service.StatisticsService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisticsServiceImplTest {

    private ConcurrentTransactionsCache concurrentTransactionsCache;

    private StatisticsService classUnderTest;

    @Before
    public void setUp() {
        concurrentTransactionsCache = new ConcurrentTransactionsCache();
        classUnderTest = new StatisticsServiceImpl(concurrentTransactionsCache);
    }

    @Test
    public void get_shouldReturnDefaultDataWhenThereIsNoDataInStatisticsCache() {

        //given
        clearStatisticsCache();
        StatisticsData expectedStatisticsData = StatisticsFixtures.getDefaultStatisticData();

        //when
        StatisticsData actualStatisticsData = classUnderTest.get();

        //then
        assertEquals(actualStatisticsData, expectedStatisticsData);
    }

    @Test
    public void compute_shouldStoreNewEntryInStatisticCache() {

        //given
        clearStatisticsCache();
        Transaction transaction = new Transaction(100d, System.currentTimeMillis());

        //when
        classUnderTest.calculate(transaction, System.currentTimeMillis());

        //then
        assertEquals(1, concurrentTransactionsCache.getData().size());
    }

    @Test
    public void compute_shouldStoreOnlyOneEntryInStatisticsCacheWhenTwoTransactionHasSameTimestamp() {

        //given
        clearStatisticsCache();
        Long timestamp = System.currentTimeMillis();
        Transaction transaction1 = new Transaction(100d, timestamp);
        Transaction transaction2 = new Transaction(200d, timestamp);

        //when
        classUnderTest.calculate(transaction1, timestamp);
        classUnderTest.calculate(transaction2, timestamp);

        //then
        assertEquals(1, concurrentTransactionsCache.getData().size());
    }

    @Test
    public void compute_shouldStoreTwoEntryInStatisticsCacheWhenTwoTransactionAreOfDifferentSeconds() {

        //given
        clearStatisticsCache();
        Long timestamp = System.currentTimeMillis();
        Transaction transaction1 = new Transaction(100d, timestamp);
        Transaction transaction2 = new Transaction(200d, timestamp + 1500);

        //when
        classUnderTest.calculate(transaction1, timestamp);
        classUnderTest.calculate(transaction2, timestamp);

        //then
        assertEquals(2, concurrentTransactionsCache.getData().size());
    }

    private void clearStatisticsCache() {
        concurrentTransactionsCache.getData().clear();
    }
}