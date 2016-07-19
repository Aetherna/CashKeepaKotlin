package com.aethernadev.cashkeepakotlin.setup

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.main.MainActivity
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.text
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.support.v4.UI
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-16.
 */

class SetupLimitFragment : BaseFragment<SetupLimitPresenter, SetupLimitUI>(), SetupLimitUI {
    override fun moveToNextStep() {
        (activity as MainActivity).showNextSetupStep()
    }

    override fun getUI(): SetupLimitUI {
        return this;
    }

    val setupPresenter: SetupLimitPresenter  by injector.instance()
    var limitType: RadioGroup? = null
    var limitAmount: EditText? = null
    var limitCurrency: Spinner? = null;
    var errorMessage: TextInputLayout? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        injector.inject((activity.application as CKApp).kodein)
        val view = UI {
            verticalLayout(theme = R.style.Match) {
                textView {
                    textResource = R.string.add_your_limit
                }.lparams(width = matchParent) {
                    horizontalMargin = dip(5)
                    topMargin = dip(10)
                }
                relativeLayout() {
                    errorMessage = textInputLayout {
                        limitAmount = editText(text = "10") {
                            inputType = InputType.TYPE_CLASS_NUMBER
                        }.apply {
                            addTextChangedListener(clearErrorOnTextChange())
                        }
                    }.lparams(width = matchParent) {
                        alignParentLeft()
                        leftOf(33)
                    }
                    limitCurrency = spinner {
                        id = 33
                        adapter = ArrayAdapter<CurrencyUnit>(context, R.layout.support_simple_spinner_dropdown_item, getSupportedCurrencies())
                    }.lparams {
                        alignParentRight()
                    }
                }
                relativeLayout {
                    limitType = radioGroup {
                        ExpenseLimitType.values().forEach {
                            radioButton {
                                text = when (it) {
                                    ExpenseLimitType.DAILY -> text(R.string.per_day)
                                    ExpenseLimitType.WEEKLY -> text(R.string.per_week)
                                    ExpenseLimitType.MONTHLY -> text(R.string.per_month)
                                }
                                tag = it
                            }
                        }
                    }.apply {
                        orientation = LinearLayout.HORIZONTAL
                        check(1)
                        setOnCheckedChangeListener { radioGroup, i -> }
                    }
                }
                button(theme = R.style.PrimaryButton, text = "Next") {
                    onClick {
                        validateAndProceed()
                    }
                }.lparams(width = matchParent) {
                    horizontalMargin = dip(5)
                    topMargin = dip(10)
                }
            }
        }.view
        presenter = setupPresenter
        return view
    }

    fun validateAndProceed() {
        if (limitAmount?.text.isNullOrBlank()) {
            errorMessage?.error = "AUCH"
        } else {
            presenter?.onNextStep(userInsertedLimit(), userChosenLimitType())
        }
    }

    fun clearErrorOnTextChange(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                errorMessage?.error = null
            }

        }
    }

    fun userChosenLimitType(): ExpenseLimitType {
        val limitType = limitType ?: return limitType?.getChildAt(0)?.tag as ExpenseLimitType;
        val checkedPosition = limitType.checkedRadioButtonId
        val type = limitType.getChildAt(checkedPosition - 1)?.tag as ExpenseLimitType

        return type
    }

    fun userInsertedLimit(): Money {
        val selectedCurrency: CurrencyUnit = limitCurrency!!.selectedItem as CurrencyUnit
        val selectedAmount: String = limitAmount!!.text.toString()

        return Money.of(selectedCurrency, BigDecimal.valueOf(selectedAmount.toLong()))
    }

    fun getSupportedCurrencies(): List<CurrencyUnit> {
        val locale = resources.configuration.locale
        val current = listOf(CurrencyUnit.getInstance(locale))
        val common = listOf(CurrencyUnit.USD, CurrencyUnit.EUR, CurrencyUnit.GBP, CurrencyUnit.JPY)

        return current.union(common).toList()
    }
}

class SetupLimitPresenter(val interactor: SetupInteractor) : BasePresenter<SetupLimitUI>() {

    fun onNextStep(amount: Money, limitType: ExpenseLimitType) {
        interactor.saveLimit(amount, limitType);
        presentOn { ui: SetupLimitUI? -> ui?.moveToNextStep() }
    }

}

interface SetupLimitUI {
    fun moveToNextStep()

}

