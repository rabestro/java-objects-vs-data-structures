package com.epam.engx.cleandesign.worker;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;

public final class SeniorWorker extends AbstractWorker {
    private static final BigDecimal SALARY_FACTOR = BigDecimal.valueOf(1.2);
    private static final BigDecimal BONUS_FACTOR = BigDecimal.valueOf(1.5);

    public SeniorWorker(Payment payment, Performance performance) {
        super(payment, performance);
    }

    @Override
    public BigDecimal salary(Surface surface) {
        return super.salary(surface).multiply(SALARY_FACTOR);
    }

    @Override
    public BigDecimal personalBonus(BigDecimal vendorBonus) {
        return vendorBonus.multiply(BONUS_FACTOR);
    }
}
