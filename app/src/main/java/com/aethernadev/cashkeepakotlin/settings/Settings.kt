package com.aethernadev.cashkeepakotlin.settings

import org.joda.money.CurrencyUnit

/**
 * Created by Aetherna on 2016-08-04.
 */
interface Settings {
    fun getCurrency(): CurrencyUnit
}

class AppSettings(val currencyUnit: CurrencyUnit) : Settings {
    override fun getCurrency(): CurrencyUnit {
        return currencyUnit
    }

}