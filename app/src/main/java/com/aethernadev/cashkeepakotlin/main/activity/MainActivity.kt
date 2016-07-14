package com.aethernadev.cashkeepakotlin.main.activity

import android.os.Bundle
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseActivity
import org.jetbrains.anko.*
import java.math.BigDecimal


class MainActivity : BaseActivity<MainPresenter, MainUI>(), MainUI {

    val mainPresenter: MainPresenter by injector.instance()
    var textView: TextView? = null

    override fun displayOutstandingLimit(code: String, amount: BigDecimal) {
        textView?.text = amount.toPlainString() + " " + code
    }

    override fun displaySnackBar() {
        toast("Sob sob snackbar")
    }

    override fun getUI(): MainUI {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject((application as CKApp).kodein)
        verticalLayout(theme = R.style.MainScreenContainer) {
            textView = textView(theme = R.style.StandardFont) {
                text = "Hello Anko!"
            }
            button(text = "Click meh!") {
                onClick {
                    presenter?.onClickMeh()
                }
            }
        }
        presenter = mainPresenter
        presenter?.loadLimit()
    }
}



