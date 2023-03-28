package com.epam.engx.cleandesign;

import com.epam.engx.cleandesign.worker.Worker;
import com.epam.engx.cleandesign.workscope.WorkScope;

import java.math.BigDecimal;

public record Assignment(Worker worker, WorkScope workScope, BigDecimal bonus) implements FundBalance {

    @Override
    public BigDecimal fundBalance() {
        return workScope.bill()
                .subtract(worker.salary(workScope))
                .subtract(worker.personalBonus(bonus));
    }
}
