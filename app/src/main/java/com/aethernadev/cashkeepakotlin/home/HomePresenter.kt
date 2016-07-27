package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import org.joda.money.Money
import rx.Subscriber
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class HomePresenter(val interactor: HomeInteractor) : BasePresenter<HomeUI>() {

    fun onAddExpenseClick() {
        interactor.getCategories().subscribe(GetCategoriesSubscriber())
    }

    open inner class GetCategoriesSubscriber : Subscriber<List<Category>>() {
        override fun onNext(categories: List<Category>) {
            presentOn({ ui: HomeUI? -> ui?.displayAddExpenseDialog(categories) })
        }

        override fun onCompleted() {
        }

        override fun onError(e: Throwable?) {
            presentOn { ui: HomeUI? -> ui?.displayError() }
        }
    }

    fun loadLimit() {
        interactor.getTodayOutstandingLimit().subscribe(GetOutstandingLimit())
    }

    open inner class GetOutstandingLimit : Subscriber<Money>() {
        override fun onNext(outstandingLimit: Money) {
            val limitWithCurrency = { ui: HomeUI? -> ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount) }
            presentOn(limitWithCurrency)
        }

        override fun onCompleted() {
        }

        override fun onError(e: Throwable?) {
            presentOn { ui: HomeUI? -> ui?.displayError() }
        }
    }

    fun addExpense(expense: Expense) {
        interactor.addExpense(expense) //todo async
        loadLimit()
    }
}

interface HomeUI {
    fun displayAddExpenseDialog(categories: List<Category>)

    fun displayOutstandingLimit(code: String, amount: BigDecimal)

    fun displayError()
}