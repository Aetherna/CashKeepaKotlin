package com.aethernadev.cashkeepakotlin

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */

interface Repo {
    fun getTodayOutstandingLimit(): Money
}

class CashKeepaRepo : Repo {
    override fun getTodayOutstandingLimit(): Money {
        return Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(100));
    }

}