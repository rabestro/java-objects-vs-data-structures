package com.epam.engx.cleandesign;

import java.math.BigDecimal;

public final class VendorBonus extends BigDecimal {
    private VendorBonus(double bonus) {
        super(bonus);
    }

    public static VendorBonus of(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("the vendor bonus must be a positive number or zero");
        }
        return new VendorBonus(bonus);
    }

    public static VendorBonus noBonus() {
        return new VendorBonus(0);
    }
}
