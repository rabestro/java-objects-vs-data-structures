package com.epam.engx.cleandesign;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

public final class FundValuation implements FundBalance {
    private final Collection<? extends FundBalance> assignments;

    private FundValuation(Collection<? extends FundBalance> assignments) {
        Objects.requireNonNull(assignments);
        this.assignments = assignments;
    }

    public static FundValuation of(FundBalance... assignments) {
        return new FundValuation(Arrays.asList(assignments));
    }

    @Override
    public BigDecimal fundBalance() {
        return assignments.stream()
                .map(FundBalance::fundBalance)
                .reduce(ZERO, BigDecimal::add);
    }
}
