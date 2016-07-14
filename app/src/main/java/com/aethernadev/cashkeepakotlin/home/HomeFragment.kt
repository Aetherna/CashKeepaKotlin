package com.aethernadev.cashkeepakotlin.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.snackbar
import org.jetbrains.anko.*
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
class HomeFragment : BaseFragment<HomePresenter, HomeUI>(), HomeUI {

    var textView: TextView? = null

    override fun getUI(): HomeUI {
        return this
    }

    override fun displayOutstandingLimit(code: String, amount: BigDecimal) {
        textView?.text = amount.toPlainString() + " " + code
    }

    override fun displaySnackBar() {
        snackbar("Sob sob snackbar", Snackbar.LENGTH_LONG)
    }



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout {
                textView = textView(theme = R.style.StandardFont) {
                    text = "Hello Anko!"
                }
                button(text = "Click meh!") {
                    onClick {
                        presenter?.onClickMeh()
                    }
                }
            }
        }.view
    }
 }
