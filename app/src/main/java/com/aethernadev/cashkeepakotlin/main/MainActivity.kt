package com.aethernadev.cashkeepakotlin.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseActivity
import com.aethernadev.cashkeepakotlin.home.HomeFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseDialogFragment
import com.aethernadev.cashkeepakotlin.setup.SetupFragment
import com.aethernadev.cashkeepakotlin.snackbar
import org.jetbrains.anko.findOptional


class MainActivity : BaseActivity<MainPresenter, MainUI>(), MainUI {

    val mainPresenter: MainPresenter by injector.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject((application as CKApp).kodein)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findOptional(R.id.toolbar))
        presenter = mainPresenter
        presenter?.loadView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        snackbar("Show menu")
        return true
    }

    override fun getUI(): MainUI {
        return this
    }

    override fun loadSetupView() {
        displayFragment(SetupFragment())//todo lazy inject
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



