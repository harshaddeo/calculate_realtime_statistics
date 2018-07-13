package com.calculate.realtime.statistics.service.impl;

import com.calculate.realtime.statistics.config.CommonConstants;
import com.calculate.realtime.statistics.model.Transaction;
import com.calculate.realtime.statistics.exception.ErrorCode;
import com.calculate.realtime.statistics.exception.ApplicationException;
import com.calculate.realtime.statistics.service.StatisticsService;
import com.calculate.realtime.statistics.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final StatisticsService statisticsService;

    @Autowired
    public TransactionServiceImpl(StatisticsService statisticsService) {
            this.statisticsService = statisticsService;
    }

    @Override
    public Boolean add(Transaction transaction) {

        validateTransaction(transaction);
        if (isTransactionTimestampUnderInterestInterval(transaction.getTimestamp())) {
            statisticsService.calculate(transaction, System.currentTimeMillis());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void validateTransaction(Transaction transaction) {
        if (Objects.isNull(transaction)) {
            throw new ApplicationException(ErrorCode.VALIDATION_EMPTY_REQUEST_BODY);
        }
        if (Objects.isNull(transaction.getAmount())) {
            throw new ApplicationException(ErrorCode.VALIDATION_MISSING_AMOUNT);
        }
        if (Objects.isNull(transaction.getTimestamp())) {
            throw new ApplicationException(ErrorCode.VALIDATION_MISSING_TIMESTAMP);
        }
    }

    private Boolean isTransactionTimestampUnderInterestInterval(Long timestamp) {
        return timestamp > (System.currentTimeMillis() - CommonConstants.REQUIRED_INTERVAL_IN_MILLISECONDS);
    }

}
