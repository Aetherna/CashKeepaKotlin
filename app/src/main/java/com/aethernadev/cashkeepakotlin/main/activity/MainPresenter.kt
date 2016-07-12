package com.aethernadev.cashkeepakotlin.main.activity

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.base.UIAction

import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class MainPresenter(val interactor: MainInteractor) : BasePresenter<MainUI>() {

    fun onClickMeh() {
        var action = present { ui?.displaySnackBar() }
        present { action }
    }

    fun loadLimit() {
        var outstandingLimit = interactor.getTodayOutstandingLimit();
        var action: UIAction<MainUI> = object : UIAction<MainUI> { //todo try to use function
            override fun executeOnUI(ui: MainUI) {
                ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount)
            }
        }
        presentAction(action);
    }
}

interface MainUI {
    fun displaySnackBar()

    fun displayOutstandingLimit(code: String, amount: BigDecimal)
}