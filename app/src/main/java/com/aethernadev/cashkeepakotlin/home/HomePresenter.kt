package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.models.Category
import rx.Subscriber
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-11.
 */
class HomePresenter(val interactor: HomeInteractor) : BasePresenter<HomeUI>() {

    fun onAddExpenseClick() {
        interactor.getCategories().subscribe(GetCategoriesSubscriber())
    }

    fun loadLimit() {
        val outstandingLimit = interactor.getTodayOutstandingLimit()
        val limitWithCurrency = { ui: HomeUI? -> ui?.displayOutstandingLimit(outstandingLimit.currencyUnit.code, outstandingLimit.amount) }
        presentOn(limitWithCurrency)
    }

    open inner class GetCategoriesSubscriber : Subscriber<List<Category>>() {
        override fun onNext(categories: List<Category>) {
            presentOn({ ui: HomeUI? -> ui?.displaySnackBar(categories) })
        }

        override fun onCompleted() {
        }

        override fun onError(e: Throwable?) {
            presentOn { ui: HomeUI? -> ui?.displayError() }
        }
    }
}

interface HomeUI {
    fun displaySnackBar(categories: List<Category>)

    fun displayOutstandingLimit(code: String, amount: BigDecimal)

    fun displayError()
}