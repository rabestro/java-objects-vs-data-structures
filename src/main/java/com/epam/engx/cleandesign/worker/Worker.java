package com.epam.engx.cleandesign.worker;

import com.epam.engx.cleandesign.workscope.Surface;

import java.math.BigDecimal;

public interface Worker {
    BigDecimal salary(Surface surface);

    BigDecimal personalBonus(BigDecimal vendorBonus);
}
