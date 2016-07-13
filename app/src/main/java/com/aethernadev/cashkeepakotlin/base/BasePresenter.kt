package com.aethernadev.cashkeepakotlin.base

import java.util.*

/**
 * Created by Aetherna on 2016-07-13.
 */

open class BasePresenter<UI>() {

    var pendingUIActions: Queue<(ui: UI) -> Unit?> = LinkedList()
    var ui: UI? = null


    fun presentOn(action: (ui: UI?) -> Unit?) {
        if (ui != null) {
            action(this.ui)
        } else {
            pendingUIActions.add(action)
        }
    }

    fun attach(ui: UI) {
        this.ui = ui
        while (pendingUIActions.isNotEmpty()) {
            pendingUIActions.poll().invoke(ui)
        }
    }

    fun detachUI() {
        this.ui = null;
    }

}