package com.epam.engx.cleandesign.workscope;

import java.math.BigDecimal;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

@FunctionalInterface
public interface Surface {

    static Surface square(double side) {
        return square(Side.of(side));
    }

    static Surface square(Side side) {
        return rectangle(side, side);
    }

    static Surface rectangle(Side a, Side b) {
        return () -> a.multiply(b);
    }

    static Surface rectangle(double a, double b) {
        return rectangle(Side.of(a), Side.of(b));
    }

    static Surface summing(Collection<? extends Surface> surfaces) {
        return () -> surfaces.stream().map(Surface::area).reduce(ZERO, BigDecimal::add);
    }

    static Surface zero() {
        return () -> ZERO;
    }

    BigDecimal area();

    default Surface subtract(Surface aperture) {
        return () -> this.area().subtract(aperture.area());
    }

    default Surface add(Surface aperture) {
        return () -> this.area().add(aperture.area());
    }
}
