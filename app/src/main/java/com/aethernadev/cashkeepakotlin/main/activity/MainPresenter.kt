package com.aethernadev.cashkeepakotlin.main.activity

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class MainPresenter(val interactor: MainInteractor) : BasePresenter<MainUI>() {

    fun onClickMeh() {
        presentOn({ ui: MainUI? -> ui?.displaySnackBar() })
    }

    fun loadLimit() {
        var outstandingLimit = interactor.getTodayOutstandingLimit();
        var limitWithCurrency = { ui: MainUI? -> ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount) }
        presentOn(limitWithCurrency)
    }
}

interface MainUI {
    fun displaySnackBar()

    fun displayOutstandingLimit(code: String, amount: BigDecimal)
}