package com.calculate.realtime.statistics.model;

import java.util.Objects;

public class StatisticsData {

    private Double sum;
    private Double max;
    private Double min;
    private Long count;
    private Double avg;

    public StatisticsData(Double sum, Double max, Double min, Long count, Double avg) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.count = count;
        this.avg = avg;
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

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public StatisticsData(StatisticsInfo statisticsInfo) {
        this.sum = statisticsInfo.getSum();
        this.max = statisticsInfo.getMax();
        this.min = statisticsInfo.getMin();
        this.count = statisticsInfo.getCount();
    }

    public StatisticsData() {
        this.sum = 0.00;
        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;
        this.count = 0L;
        this.avg = 0.00;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatisticsData)) return false;
        StatisticsData that = (StatisticsData) o;
        return Objects.equals(getSum(), that.getSum()) &&
                Objects.equals(getMax(), that.getMax()) &&
                Objects.equals(getMin(), that.getMin()) &&
                Objects.equals(getCount(), that.getCount()) &&
                Objects.equals(getAvg(), that.getAvg());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSum(), getMax(), getMin(), getCount(), getAvg());
    }
}
