package com.aethernadev.cashkeepakotlin.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseActivity
import com.aethernadev.cashkeepakotlin.home.HomeFragment
import com.aethernadev.cashkeepakotlin.welcome.WelcomeFragment
import org.jetbrains.anko.frameLayout


class MainActivity : BaseActivity<MainPresenter, MainUI>(), MainUI {

    val fragmentContainerId: Int = 1
    val mainPresenter: MainPresenter by injector.instance()
    var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject((application as CKApp).kodein)

        frameLayout(theme = R.style.MainScreenContainer) {
            id = fragmentContainerId
        }

        presenter = mainPresenter
        presenter?.loadView()
    }

    override fun getUI(): MainUI {
        return this
    }

    override fun loadWelcomeView() {
        displayFragment(WelcomeFragment())//todo lazy inject
    }

    override fun loadHomeView() {
        displayFragment(HomeFragment()) //todo lazy inject
    }

    fun displayFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction?.replace(fragmentContainerId, fragment)
        transaction?.commit()
    }

    fun onConfigDone() {
        presenter?.onConfigDone()
    }


}



