package com.aethernadev.cashkeepakotlin.base

import android.app.Activity
import com.aethernadev.cashkeepakotlin.main.activity.MainUI

/**
 * Created by Aetherna on 2016-07-11.
 */
abstract class BaseActivity<Presenter : BasePresenter<UI>, UI> : Activity() {

    var presenter: Presenter? = null;

    abstract fun getUI(): UI

    fun setupPresenter(presenter: Presenter?) {
        this.presenter = presenter;
    }

    override fun onPause() {
        super.onPause()
        presenter?.detachUI()
    }

    override fun onResume() {
        super.onResume()
        presenter?.attach(getUI())
    }
}

open class BasePresenter<UI>() {

    var pendingUIActions: MutableList<UIAction<UI>> = mutableListOf()
    var ui: UI? = null

    fun presentAction(uiAction: UIAction<UI>) {
        if (ui != null) {
            uiAction.executeOnUI(ui as UI) //todo check as
        } else {
            pendingUIActions.add(uiAction)
        }
    }

    fun < T> present(body: () -> T): T {
        val action: UIAction<UI> =  uIAction<UI> { //todo wtf
            override fun executeOnUI(ui: UI) {
                body()
            }
        }
        return action
    }

    fun attach(ui: UI) {
        this.ui = ui
        if (pendingUIActions.isNotEmpty()) {
            pendingUIActions.forEach {
                it.executeOnUI(ui)
            }
            pendingUIActions.clear() //todo hymm mutithread
        }
    }

    fun detachUI() {
        this.ui = null;
    }

}

interface UIAction<UI> {
    fun executeOnUI(ui: UI)
}