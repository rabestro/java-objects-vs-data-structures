package com.epam.engx.cleandesign.workscope;

import java.math.BigDecimal;

@FunctionalInterface
public interface Billable {

    BigDecimal bill();
}
