package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class HomePresenter(val interactor: HomeInteractor) : BasePresenter<HomeUI>() {

    fun onClickMeh() {
        presentOn({ ui: HomeUI? -> ui?.displaySnackBar() })
    }

    fun loadLimit() {
        val outstandingLimit = interactor.getTodayOutstandingLimit()
        val limitWithCurrency = { ui: HomeUI? -> ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount) }
        presentOn(limitWithCurrency)
    }
}

interface HomeUI {
    fun displaySnackBar()

    fun displayOutstandingLimit(code: String, amount: BigDecimal)
}