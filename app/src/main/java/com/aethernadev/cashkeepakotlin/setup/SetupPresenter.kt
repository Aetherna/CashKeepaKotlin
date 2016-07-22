package com.aethernadev.cashkeepakotlin.setup

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import org.joda.money.Money

/**
 * Created by Aetherna on 2016-07-21.
 */

class SetupPresenter(val interactor: SetupInteractor) : BasePresenter<SetupUI>() {

    fun onDoneClick(amount: Money, limitType: ExpenseLimitType) {
        interactor.saveLimit(amount, limitType)
        interactor.saveInitialCategories()
        presentOn { ui: SetupUI? -> ui?.onSetupDone() }
    }
}

interface SetupUI {
    fun onSetupDone()
}