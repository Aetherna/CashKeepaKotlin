package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money
import org.joda.time.DateTime
import rx.Observable

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getTodayOutstandingLimit(): Money {

        val limit = repo.getNewestLimit() //todo: handle limit type to end date
        val expenses: List<Expense> = repo.getExpensesBetween(limit.created, DateTime.now())

        val outstanding = limit.amount
        expenses.forEach {
            outstanding.minus(it.amount)
        }

        return outstanding
    }

    open fun getCategories(): Observable<List<Category>> {
        return wrapObservableOperation({ repo.getCategories() })

    }


}