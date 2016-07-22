package com.aethernadev.cashkeepakotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.home.addexpense.AddExpenseDialogFragment
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.snackbar
import com.aethernadev.cashkeepakotlin.text
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
class HomeFragment : BaseFragment<HomePresenter, HomeUI>(), HomeUI {

    val homePresenter: HomePresenter by injector.instance()
    var textView: TextView? = null

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun getUI(): HomeUI {
        return this
    }

    override fun displayOutstandingLimit(code: String, amount: BigDecimal) {
        textView?.text = amount.toPlainString() + " " + code
    }

    override fun displayAddExpenseDialog(categories: List<Category>) {
        val addExpenseDialog = AddExpenseDialogFragment.newInstance(categories)
//        addExpenseDialog.setTargetFragment(this, 0)
//        (activity as MainActivity).displayDialog(addExpenseDialog)
         displayDialog(addExpenseDialog)
    }

    override fun displayError() {
        snackbar("Ups error")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        injector.inject((activity.application as CKApp).kodein)
        val view = UI {
            verticalLayout {
                textView = textView(theme = R.style.StandardFont) {
                    text = "Hello Anko........!"
                }
                button(text = text(R.string.add_expense)) {
                    onClick {
                        presenter?.onAddExpenseClick()
                    }
                }
            }
        }.view
        presenter = homePresenter
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.loadLimit()
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
