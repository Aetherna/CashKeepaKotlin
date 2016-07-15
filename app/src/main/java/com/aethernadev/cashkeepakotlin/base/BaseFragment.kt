package com.aethernadev.cashkeepakotlin.base


import android.support.v4.app.Fragment
import com.github.salomonbrys.kodein.KodeinInjector


/**
 * Created by Aetherna on 2016-07-11.
 */
abstract class BaseFragment<Presenter : BasePresenter<UI>, UI> : Fragment() {

    val injector = KodeinInjector()
    open var presenter: Presenter? = null

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

