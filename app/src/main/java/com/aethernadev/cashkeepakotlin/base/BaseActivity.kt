package com.aethernadev.cashkeepakotlin.base

import android.app.Activity
import java.util.*


/**
 * Created by Aetherna on 2016-07-11.
 */
abstract class BaseActivity<Presenter : BasePresenter<UI>, UI> : Activity() {

    var presenter: Presenter? = null;

    abstract fun getUI(): UI

    override fun onPause() {
        super.onPause()
        presenter?.detachUI()
    }

    override fun onResume() {
        super.onResume()
        presenter?.attach(getUI())
    }
}

