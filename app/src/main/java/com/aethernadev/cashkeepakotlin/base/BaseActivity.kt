package com.aethernadev.cashkeepakotlin.base

import android.app.Activity
import java.util.*


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
