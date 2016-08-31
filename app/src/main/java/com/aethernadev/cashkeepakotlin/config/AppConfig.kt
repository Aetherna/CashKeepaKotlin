package com.aethernadev.cashkeepakotlin.config


import org.joda.money.CurrencyUnit


/**
 * Created by Aetherna on 2016-08-31.
 */
open class AppConfig(val currencyConfig: CurrencyConfig) {

    open fun getCurrency(): CurrencyUnit {
        return currencyConfig.appCurrency!!
    }
}

open class CurrencyConfig(configInteractor: ConfigInteractor) {
    var appCurrency: CurrencyUnit? = null;

    init {
        configInteractor.getSettings().subscribe({ appCurrency = it })
    }
}

