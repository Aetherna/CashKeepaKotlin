package com.aethernadev.cashkeepakotlin.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.snackbar
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

    override fun getUI(): HomeUI {
        return this
    }

    override fun displayOutstandingLimit(code: String, amount: BigDecimal) {
        textView?.text = amount.toPlainString() + " " + code
    }

    override fun displaySnackBar(categories: List<Category>) {
        val categoriesList = categories.map { c -> c.name }.reduce { s1, s2 -> s1 + s2 }
        snackbar("Sob sob snackbar : " + categoriesList, Snackbar.LENGTH_LONG)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        injector.inject((activity.application as CKApp).kodein)
        val view = UI {
            verticalLayout {
                textView = textView(theme = R.style.StandardFont) {
                    text = "Hello Anko........!"
                }
                button(text = "Click meh!") {
                    onClick {
                        presenter?.onClickMeh()
                    }
                }
            }
        }.view
        presenter = homePresenter;
        return view;
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.loadLimit()
    }
}
