package com.epam.engx.cleandesign.workscope;

import com.epam.engx.cleandesign.workcost.PriceFunction;

import java.math.BigDecimal;

public final class Zone implements WorkScope {
    private final PriceFunction priceFunction;
    private final Surface surface;
    private final Surface apertures;

    public Zone(PriceFunction priceFunction, Surface surface, Surface apertures) {
        this.priceFunction = priceFunction;
        this.surface = surface;
        this.apertures = apertures;
    }

    public Zone(PriceFunction priceFunction, Surface surface) {
        this(priceFunction, surface, Surface.zero());
    }

    @Override
    public BigDecimal area() {
        return surface.area().subtract(apertures.area());
    }

    @Override
    public BigDecimal bill() {
        return priceFunction.apply(this);
    }
}
