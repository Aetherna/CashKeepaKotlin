package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.config.AppConfig
import com.aethernadev.cashkeepakotlin.currencyCode
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.moneyFrom
import org.joda.money.CurrencyUnit
import rx.Subscriber
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class HomePresenter(val interactor: HomeInteractor, val appConfig: AppConfig) : BasePresenter<HomeUI>() {


    fun onAddExpenseClick() {
        interactor.getCategories().subscribe(object : Subscriber<List<Category>>() {
            override fun onNext(categories: List<Category>) {
                presentOn({ ui: HomeUI? -> ui?.displayAddExpenseDialog(categories, appConfig.getCurrency()) })
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                presentOn { ui: HomeUI? -> ui?.displayError() }
            }
        })
    }

    fun loadLimit() {

        interactor.getTodayOutstandingLimit().subscribe(object : Subscriber<LimitSpendings>() {
            override fun onNext(limitAndSpendings: LimitSpendings) {
                val available = limitAndSpendings.available
                val spent = limitAndSpendings.spent
                val limitWithCurrency = { ui: HomeUI? -> ui?.displayAvailableAndSpent(available.amount, available.currencyCode(), spent.amount) }
                presentOn(limitWithCurrency)
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                presentOn { ui: HomeUI? -> ui?.displayError() } //todo critical error "exit the app"
            }
        })
    }


    fun addExpense(amount: String, category: Category) {
        //todo handle currency
        val expense = Expense(amount = moneyFrom(appConfig, amount), category = category)
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

    fun displayAvailableAndSpent(available: BigDecimal, code: String, spent: BigDecimal)

    fun displayError()
}