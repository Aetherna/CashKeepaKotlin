package com.aethernadev.cashkeepakotlin.main

import com.aethernadev.cashkeepakotlin.base.BasePresenter

/**
 * Created by Aetherna on 2016-07-14.
 */
class MainPresenter(val interactor: MainInteractor): BasePresenter<MainUI>() {

    fun loadView() {
        if (interactor.isAppConfigured()) {
            presentOn { ui: MainUI? -> ui?.loadHomeView() }
        } else {
            presentOn {  ui: MainUI? -> ui?.loadWelcomeView() }
        }
    }

    fun onConfigDone(){
        interactor.setAppIsConfigured()
        presentOn { ui: MainUI? -> ui?.loadHomeView() }
    }
}


interface MainUI{
    fun loadWelcomeView()
    fun loadHomeView()
}