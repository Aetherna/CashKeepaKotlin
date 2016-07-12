package com.aethernadev.cashkeepakotlin.main.activity

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-12.
 */
open class MainInteractor {

    open fun getTodayOutstandingLimit(): Money {
        return Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(100));
    }
}