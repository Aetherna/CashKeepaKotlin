package com.aethernadev.cashkeepakotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseDialogFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseListener
import com.aethernadev.cashkeepakotlin.home.addexpense.ExpenseAddedData
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
class HomeFragment : BaseFragment<HomePresenter, HomeUI>(), HomeUI, AddExpenseListener {
    override fun onExpenseAdded(expense: ExpenseAddedData) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        displayDialog(addExpenseDialog)
    }

    override fun displayError() {
        snackbar("Ups error")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injector.inject((activity.application as CKApp).kodein)
        presenter = homePresenter
        retainInstance = true
        return inflater?.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.loadLimit()
        home_add_expense.setOnClickListener { presenter?.onAddExpenseClick() }


     }

    fun addExpense(expense: Expense) {
        presenter?.addExpense(expense)
    }

    fun displayDialog(dialogFragment: AddExpenseDialogFragment) {
//        val transaction = childFragmentManager.beginTransaction()
//        val previousDialog = childFragmentManager.findFragmentByTag("dialog")
//        if (previousDialog != null) {
//            transaction.remove(previousDialog)
//        }
//        dialogFragment.setTargetFragment(this, 0)
//        transaction.addToBackStack(null)
//        dialogFragment.show(transaction, "dialog")

        dialogFragment.show(childFragmentManager, "dialog")

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val previousDialog = childFragmentManager.findFragmentByTag("dialog")
        if (previousDialog != null) {
            childFragmentManager.putFragment(outState,"dialog", previousDialog)
        }

    }




}
