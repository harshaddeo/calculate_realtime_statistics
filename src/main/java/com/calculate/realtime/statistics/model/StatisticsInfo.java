package com.calculate.realtime.statistics.model;

import java.util.Objects;

public class StatisticsInfo {

    private Long timestamp;

    private Double sum;

    private Double max;

    private Double min;

    private Long count;

    public StatisticsInfo(){

    }
    public StatisticsInfo(Long timestamp, Double sum, Double max, Double min, Long count) {
        this.timestamp = timestamp;
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatisticsInfo)) return false;
        StatisticsInfo that = (StatisticsInfo) o;
        return Objects.equals(getTimestamp(), that.getTimestamp()) &&
                Objects.equals(getSum(), that.getSum()) &&
                Objects.equals(getMax(), that.getMax()) &&
                Objects.equals(getMin(), that.getMin()) &&
                Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTimestamp(), getSum(), getMax(), getMin(), getCount());
    }
}
