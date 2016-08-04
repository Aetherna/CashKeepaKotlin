package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.settings.Settings
import com.aethernadev.cashkeepakotlin.settings.SettingsInteractor
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import rx.Subscriber
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class HomePresenter(val interactor: HomeInteractor, val settingsInteractor: SettingsInteractor) : BasePresenter<HomeUI>() {

    var settings: Settings? = null

    fun onAddExpenseClick() {
        interactor.getCategories().subscribe(object : Subscriber<List<Category>>() {
            override fun onNext(categories: List<Category>) {
                presentOn({ ui: HomeUI? -> ui?.displayAddExpenseDialog(categories, settings!!.getCurrency()) })
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                presentOn { ui: HomeUI? -> ui?.displayError() }
            }
        })
    }

    fun loadLimit() {
        interactor.getTodayOutstandingLimit().subscribe(object : Subscriber<Money>() {
            override fun onNext(outstandingLimit: Money) {
                val limitWithCurrency = { ui: HomeUI? -> ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount) }
                presentOn(limitWithCurrency)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                presentOn { ui: HomeUI? -> ui?.displayError() } //todo critical error "exit the app"
            }
        })

        settingsInteractor.getSettings().subscribe(object : Subscriber<Settings>() {
            override fun onCompleted() {
            }

            override fun onNext(settings: Settings?) {
                this@HomePresenter.settings = settings
            }

            override fun onError(e: Throwable?) {
                ui?.displayError()
            }

        })
    }


    fun addExpense(amount: String, category: Category) {
        //todo handle currency
        val expense = Expense(amount = Money.of(settings!!.getCurrency(), BigDecimal.valueOf(amount.toLong())), category = category)
        interactor.addExpense(expense).subscribe(object : Subscriber<Unit>() {
            override fun onNext(t: Unit?) {
            }

            override fun onCompleted() {
                loadLimit()
            }

            override fun onError(e: Throwable?) {
                presentOn { ui: HomeUI? -> ui?.displayError() }
            }
        })
    }
}

interface HomeUI {
    fun displayAddExpenseDialog(categories: List<Category>, currencyUnit: CurrencyUnit)

    fun displayOutstandingLimit(code: String, amount: BigDecimal)

    fun displayError()
}