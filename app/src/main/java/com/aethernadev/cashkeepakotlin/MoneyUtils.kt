package com.aethernadev.cashkeepakotlin

import com.aethernadev.cashkeepakotlin.config.AppConfig
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-09-01.
 */
fun moneyFrom(currency: CurrencyUnit, amount: String): Money {
    return Money.of(currency, BigDecimal.valueOf(amount.toDouble()))
}

fun Money.currencyCode(): String {
    return currencyUnit.code
}

fun moneyFrom(appConfig: AppConfig, amount: String): Money {
    val currency = appConfig.getCurrency()
    val decimalPrecision: Int = currency.decimalPlaces
    val amountValue = BigDecimal.valueOf(amount.toDouble()).setScale(decimalPrecision)

    return Money.of(currency, amountValue)
}