package com.epam.engx.cleandesign.workcost;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

public final class ZonePrice implements PriceFunction {
    private final Collection<? extends PriceFunction> priceFunctions;

    private ZonePrice(Collection<? extends PriceFunction> priceFunctions) {
        this.priceFunctions = priceFunctions;
    }

    public static ZonePrice of(PriceFunction... priceFunctions) {
        return new ZonePrice(Arrays.asList(priceFunctions));
    }

    @Override
    public BigDecimal apply(Surface surface) {
        return priceFunctions.stream()
                .map(priceFunction -> priceFunction.apply(surface))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
