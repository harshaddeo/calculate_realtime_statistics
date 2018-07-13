package com.calculate.realtime.statistics.service.impl;

import com.calculate.realtime.statistics.model.Transaction;
import com.calculate.realtime.statistics.exception.ApplicationException;
import com.calculate.realtime.statistics.service.StatisticsService;
import com.calculate.realtime.statistics.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    private TransactionService classUnderTest;

    private StatisticsService statisticsService;

    @Before
    public void setUp() {
        statisticsService = mock(StatisticsService.class);
        classUnderTest = new TransactionServiceImpl(statisticsService);
    }

    @Test(expected = ApplicationException.class)
    public void add_shouldThrowValidationExceptionWhenTransactionIsNull() {

        //given
        Transaction transaction = null;

        //when
        classUnderTest.add(transaction);

    }

    @Test(expected = ApplicationException.class)
    public void add_shouldThrowValidationExceptionWhenTransactionTimestampIsNull() {
        //given
        Transaction transaction = new Transaction(100d, null);

        //when
        classUnderTest.add(transaction);

    }

    @Test(expected = ApplicationException.class)
    public void add_shouldThrowValidationExceptionWhenTransactionAmountIsNull() {

        //given
        Transaction transaction = new Transaction(null, System.currentTimeMillis());

        //when
        classUnderTest.add(transaction);

    }

    @Test
    public void add_shouldReturnTrueWhenTransactionTimestampIsCurrentTimestamp() {

        //given
        doNothing().when(statisticsService).calculate(any(), any());
        Transaction transaction = new Transaction(100d, System.currentTimeMillis());

        //when
        Boolean result = classUnderTest.add(transaction);

        //then
        assertTrue(result);
    }

    @Test
    public void add_shouldReturnFalseWhenTransactionTimestampIsOutdated() {

        //given
        Long outdatedTimestamp = System.currentTimeMillis() - 61000L;
        Transaction transaction = new Transaction(100d, outdatedTimestamp);

        //when
        Boolean result = classUnderTest.add(transaction);

        //then
        assertFalse(result);

    }
}