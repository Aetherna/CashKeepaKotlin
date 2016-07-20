package com.aethernadev.cashkeepakotlin.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseActivity
import com.aethernadev.cashkeepakotlin.color
import com.aethernadev.cashkeepakotlin.home.HomeFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseDialogFragment
import com.aethernadev.cashkeepakotlin.setup.SetupCategoriesFragment
import com.aethernadev.cashkeepakotlin.setup.SetupLimitFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout


class MainActivity : BaseActivity<MainPresenter, MainUI>(), MainUI {

    val mainPresenter: MainPresenter by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject((application as CKApp).kodein)

        coordinatorLayout(theme = R.style.CoordinatorLayout) {
            appBarLayout(theme = R.style.AppBar) {
                toolbar(theme = R.style.Toolbar) {
                    id = R.id.toolbar
                    titleResource = R.string.app_name
                }.apply {
                    setTitleTextColor(color(R.color.toolbarTextColor))
                }
            }.lparams {
                width = matchParent
            }
            include<RelativeLayout>(R.layout.content_main) {
                id = R.id.content
            }
        }

        setSupportActionBar(findOptional(R.id.toolbar))
        presenter = mainPresenter
        presenter?.loadView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return true
    }

    override fun getUI(): MainUI {
        return this
    }

    override fun loadSetupView() {
        displayFragment(SetupLimitFragment())//todo lazy inject
    }

    override fun loadHomeView() {
        displayFragment(HomeFragment()) //todo lazy inject
    }

    fun displayFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction?.replace(R.id.content, fragment)
        transaction?.commit()
    }

    fun onConfigDone() {
        presenter?.onConfigDone()
    }

    fun showNextSetupStep() { //todo extract interface for those
        displayFragment(SetupCategoriesFragment())
    }

    fun displayDialog(dialogFragment: AddExpenseDialogFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        val previousDialog = supportFragmentManager.findFragmentByTag("dialog")
        if (previousDialog != null) {
            transaction.remove(previousDialog)
        }
        transaction.addToBackStack(null)
        dialogFragment.show(transaction, "dialog")

    }

}



