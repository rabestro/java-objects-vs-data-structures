package com.epam.engx.cleandesign.worker;

import com.epam.engx.cleandesign.Assignment;
import com.epam.engx.cleandesign.FundBalance;
import com.epam.engx.cleandesign.workscope.Surface;
import com.epam.engx.cleandesign.workscope.WorkScope;

import java.math.BigDecimal;

abstract class AbstractWorker implements Worker {
    private final Payment payment;
    private final Performance performance;

    AbstractWorker(Payment payment, Performance performance) {
        this.payment = payment;
        this.performance = performance;
    }

    @Override
    public BigDecimal salary(Surface surface) {
        int days = performance.daysToProcess(surface);
        return payment.payForDays(days);
    }

    public FundBalance assign(WorkScope workScope, BigDecimal bonus) {
        return new Assignment(this, workScope, bonus);
    }
}
