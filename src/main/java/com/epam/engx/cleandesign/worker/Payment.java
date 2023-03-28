package com.epam.engx.cleandesign.worker;

import java.math.BigDecimal;

@FunctionalInterface
public interface Payment {
    static Payment of(double dailyRate) {
        if (dailyRate < 0) {
            throw new IllegalArgumentException("the daily rate must be a positive number");
        }
        return days -> BigDecimal.valueOf(dailyRate)
                .multiply(new BigDecimal(days));
    }

    BigDecimal payForDays(int days);
}
