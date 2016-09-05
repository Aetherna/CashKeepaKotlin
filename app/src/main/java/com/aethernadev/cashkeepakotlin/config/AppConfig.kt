package com.aethernadev.cashkeepakotlin.config


import org.joda.money.CurrencyUnit


/**
 * Created by Aetherna on 2016-08-31.
 */
open class AppConfig(configInteractor: ConfigInteractor) {
    var appCurrency: CurrencyUnit? = null

    init {
        configInteractor.getSettings().subscribe({ appCurrency = it })
    }

    open fun getCurrency(): CurrencyUnit {
        return appCurrency!!
    }
}


