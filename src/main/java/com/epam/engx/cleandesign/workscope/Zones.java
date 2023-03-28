package com.epam.engx.cleandesign.workscope;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

public class Zones implements WorkScope {
    private final Collection<? extends WorkScope> zones;

    private Zones(Collection<? extends WorkScope> zones) {
        this.zones = zones;
    }

    public static Zones of(WorkScope... zones) {
        return new Zones(Arrays.asList(zones));
    }

    @Override
    public BigDecimal bill() {
        return zones.stream()
                .map(WorkScope::bill)
                .reduce(ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal area() {
        return Surface.summing(zones).area();
    }
}
