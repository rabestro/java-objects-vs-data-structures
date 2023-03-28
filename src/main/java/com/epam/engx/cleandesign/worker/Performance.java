package com.epam.engx.cleandesign.worker;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;
import java.math.RoundingMode;

@FunctionalInterface
public interface Performance {
    static Performance of(double amountPerDay) {
        if (amountPerDay <= 0) {
            throw new IllegalArgumentException("the amount per day must be a positive number");
        }
        return surface -> surface.area()
                .divide(BigDecimal.valueOf(amountPerDay), 0, RoundingMode.CEILING)
                .intValueExact();
    }

    int daysToProcess(Surface surface);
}
