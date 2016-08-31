package com.aethernadev.cashkeepakotlin.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.aethernadev.cashkeepakotlin.*
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.main.MainActivity
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import kotlinx.android.synthetic.main.setup_fragment.*
import org.joda.money.CurrencyUnit
import org.joda.money.Money

/**
 * Created by Aetherna on 2016-07-16.
 */

class SetupFragment : BaseFragment<SetupPresenter, SetupUI>(), SetupUI {
    override fun onSetupDone() {
        (activity as MainActivity).onConfigDone()
    }

    override fun getUI(): SetupUI {
        return this;
    }

    val setupPresenter: SetupPresenter  by injector.instance()
    var checkedLimitTypePosition = 1


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injector.inject((activity.application as CKApp).kodein)
        val view = inflater?.inflate(R.layout.setup_fragment, container, false)
        presenter = setupPresenter
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup_limit_amount.textWatcher(afterTextChange = { setup_limit_amount_error?.error = null })
        setup_limit_currency.adapter = ArrayAdapter<CurrencyUnit>(context, R.layout.support_simple_spinner_dropdown_item, getSupportedCurrencies())

        ExpenseLimitType.values().forEach {
            setup_limit_type.addView(getRadioButtonFor(it))
        }
        setup_limit_type.check(checkedLimitTypePosition)
        setup_limit_type.setOnCheckedChangeListener { radioGroup, position -> checkedLimitTypePosition = position }

        setup_done.setOnClickListener {
            validateAndFinishSetup()
        }
    }

    private fun getRadioButtonFor(limitType: ExpenseLimitType): RadioButton {
        val radio = RadioButton(context)
        radio.text = when (limitType) {
            ExpenseLimitType.DAILY -> text(R.string.per_day)
            ExpenseLimitType.WEEKLY -> text(R.string.per_week)
            ExpenseLimitType.MONTHLY -> text(R.string.per_month)
        }
        radio.tag = limitType
        return radio
    }

    fun validateAndFinishSetup() {
        if (setup_limit_amount?.text.isNullOrBlank()) {
            setup_limit_amount_error?.error = text(R.string.insert_amount_error)
        } else {
            presenter?.onDoneClick(userInsertedLimit(), userChosenLimitType())
        }
    }

    fun userChosenLimitType(): ExpenseLimitType {
        return setup_limit_type.getChildAt(checkedLimitTypePosition).getTag() as ExpenseLimitType
    }

    fun userInsertedLimit(): Money {
        val selectedCurrency: CurrencyUnit = setup_limit_currency.selectedItem as CurrencyUnit
        val selectedAmount: String = setup_limit_amount.text.toString()

        return moneyFrom(selectedCurrency, selectedAmount)
    }

    fun getSupportedCurrencies(): List<CurrencyUnit> {
        val locale = resources.configuration.locale
        val current = listOf(CurrencyUnit.getInstance(locale))
        val common = listOf(CurrencyUnit.USD, CurrencyUnit.EUR, CurrencyUnit.GBP, CurrencyUnit.JPY)

        return current.union(common).toList()
    }
}


