package com.epam.engx.cleandesign.workcost;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;

public final class MaterialPrice implements PriceFunction {
    private static final BigDecimal DEFAULT_MATERIAL_AREA_FACTOR = new BigDecimal(10);

    private final BigDecimal materialAreaFactor;

    private MaterialPrice(BigDecimal materialAreaFactor) {
        this.materialAreaFactor = materialAreaFactor;
    }

    public static MaterialPrice of(double materialAreaFactor) {
        if (materialAreaFactor < 0) {
            throw new IllegalArgumentException("the material area factor must be a positive number or zero");
        }
        return new MaterialPrice(BigDecimal.valueOf(materialAreaFactor));
    }

    public static MaterialPrice norm() {
        return new MaterialPrice(DEFAULT_MATERIAL_AREA_FACTOR);
    }

    @Override
    public BigDecimal apply(Surface surface) {
        return surface.area()
                .multiply(materialAreaFactor);
    }
}
