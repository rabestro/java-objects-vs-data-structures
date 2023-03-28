package com.epam.engx.cleandesign.workcost;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;
import java.util.function.Function;

@FunctionalInterface
public interface PriceFunction extends Function<Surface, BigDecimal> {

}
