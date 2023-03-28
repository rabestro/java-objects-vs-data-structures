package com.epam.engx.cleandesign.workcost;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;

public final class WorkPrice implements PriceFunction {
    private static final BigDecimal MULTI_DAY_PRICE_FACTOR = BigDecimal.valueOf(1.1);
    private static final BigDecimal ONE_DAY_MAX_AREA = new BigDecimal(50);

    private final BigDecimal workPrice;

    private WorkPrice(double workPrice) {
        this.workPrice = BigDecimal.valueOf(workPrice);
    }

    public static WorkPrice of(double workPrice) {
        if (workPrice < 0) {
            throw new IllegalArgumentException("the work price must be a positive number or zero");
        }
        return new WorkPrice(workPrice);
    }

    private boolean isOneDayWork(Surface surface) {
        return surface.area().compareTo(ONE_DAY_MAX_AREA) < 0;
    }

    private BigDecimal oneDayCost(Surface surface) {
        return surface.area().multiply(workPrice);
    }

    private BigDecimal multiDayCost(Surface surface) {
        return oneDayCost(surface).multiply(MULTI_DAY_PRICE_FACTOR);
    }

    @Override
    public BigDecimal apply(Surface surface) {
        return isOneDayWork(surface) ? oneDayCost(surface) : multiDayCost(surface);
    }
}
