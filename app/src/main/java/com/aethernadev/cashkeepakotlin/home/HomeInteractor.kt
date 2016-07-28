package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money
import org.joda.time.DateTime
import rx.Observable

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getTodayOutstandingLimit(): Observable<Money> {

        val newestLimit: Observable<Limit> = wrapAsJustObservable({ repo.getNewestLimit() })

        val available = newestLimit.map { limit ->
            LimitSpendings(limit, repo.getExpensesBetween(limit.created, DateTime.now())).getTotalSpendings()
        }
        return available
    }

    open fun getCategories(): Observable<List<Category>> {
        return wrapAsJustObservable({ repo.getCategories() })
    }

    inner class LimitSpendings(val limit: Limit, val expenses: List<Expense>) {
        fun getTotalSpendings(): Money {
            var available = limit.amount
            expenses.forEach { expense -> available = available.minus(expense.amount) }
            return available
        }
    }

    fun addExpense(expense: Expense): Observable<Unit> {
        return wrapAsJustObservable<Unit> { repo.saveExpense(expense) }
//        repo.reposaveExpense(expense)
    }
}