package com.aethernadev.cashkeepakotlin.main.activity

import android.os.Bundle
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseActivity
import org.jetbrains.anko.*
import java.math.BigDecimal


class MainActivity : BaseActivity<MainPresenter, MainUI>(), MainUI {

    var textView: TextView? = null;

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
        val interactor = MainInteractor()
        presenter = MainPresenter(interactor);

        presenter?.loadLimit();
    }
}



