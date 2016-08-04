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
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.snackbar
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.support.v4.toast
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
class HomeFragment : BaseFragment<HomePresenter, HomeUI>(), HomeUI, AddExpenseListener {

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
        home_refresh.setOnClickListener { presenter?.loadLimit() }
    }


    fun displayDialog(dialogFragment: AddExpenseDialogFragment) {
        dialogFragment.setTargetFragment(this, 0)
        dialogFragment.show(fragmentManager, "dialog")
    }

    override fun onExpenseAdded(amount: String, category: Category) {
        toast(amount + "" + category.name)
        presenter?.addExpense(amount, category)
    }
}
