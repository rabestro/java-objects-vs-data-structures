package com.epam.engx.cleandesign.worker;

import java.math.BigDecimal;

public final class JuniorWorker extends AbstractWorker {

    public JuniorWorker(Payment payment, Performance performance) {
        super(payment, performance);
    }

    @Override
    public BigDecimal personalBonus(BigDecimal vendorBonus) {
        return vendorBonus;
    }
}
