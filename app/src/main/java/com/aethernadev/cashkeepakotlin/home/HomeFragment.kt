package com.aethernadev.cashkeepakotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseDialogFragment
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
class HomeFragment : BaseFragment<HomePresenter, HomeUI>(), HomeUI {

    val homePresenter: HomePresenter by injector.instance()

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getUI(): HomeUI {
        return this
    }

    override fun displayOutstandingLimit(code: String, amount: BigDecimal) {
        home_limit.text = amount.toPlainString() + " " + code
    }

    override fun displayAddExpenseDialog(categories: List<Category>) {
        val addExpenseDialog = AddExpenseDialogFragment.newInstance(categories)
        addExpenseDialog.setTargetFragment(this@HomeFragment, -1)
//        (activity as MainActivity).displayDialog(addExpenseDialog)
        displayDialog(addExpenseDialog)
    }

    override fun displayError() {
        snackbar("Ups error")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injector.inject((activity.application as CKApp).kodein)
        val view = inflater?.inflate(R.layout.home_fragment, container,false)
        presenter = homePresenter
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.loadLimit()
        home_add_expense.setOnClickListener { presenter?.onAddExpenseClick() }
    }

    fun displayDialog(dialogFragment: AddExpenseDialogFragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        val previousDialog = activity.supportFragmentManager.findFragmentByTag("dialog")
        if (previousDialog != null) {
            transaction.remove(previousDialog)
        }
        transaction.addToBackStack(null)
        dialogFragment.show(transaction, "dialog")

    }
}
