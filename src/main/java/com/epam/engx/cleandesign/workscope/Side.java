package com.epam.engx.cleandesign.workscope;

import java.math.BigDecimal;

public final class Side extends BigDecimal {

    private Side(String meters) {
        super(meters);
    }

    public static Side of(double meters) {
        return of(BigDecimal.valueOf(meters));
    }

    public static Side of(BigDecimal meters) {
        if (meters.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("the side of the surface must be a positive number");
        }
        return new Side(meters.toPlainString());
    }
}
