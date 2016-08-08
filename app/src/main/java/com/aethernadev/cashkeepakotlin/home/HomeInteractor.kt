package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money
import org.joda.time.DateTime
import rx.Observable

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getTodayOutstandingLimit(): Observable<LimitSpendings> {

        val newestLimit: Observable<Limit> = wrapAsJustObservable({ repo.getNewestLimit() })

        return newestLimit.map { limit ->
            LimitSpendings(limit, repo.getExpensesBetween(limitDates(limit, DateTime.now()).first, DateTime.now()))
        }
    }

    open fun getCategories(): Observable<List<Category>> {
        return wrapAsJustObservable({ repo.getCategories() })
    }

    fun addExpense(expense: Expense): Observable<Unit> {
        return wrapAsJustObservable<Unit> { repo.saveExpense(expense) }
    }
}

open class LimitSpendings(val limit: Limit, expenses: List<Expense>) {

    var available: Money = Money.ofMinor(limit.amount.currencyUnit, 0)
    var spent: Money = Money.ofMinor(limit.amount.currencyUnit, 0)

    init {
        expenses.forEach { expense ->
            spent = spent.plus(expense.amount)
        }
        available = limit.amount.minus(spent)
    }
}

fun limitDates(limit: Limit, now: DateTime): Pair<DateTime, DateTime> {
    val dates: Pair<DateTime, DateTime> =
            when (limit.type) {
                ExpenseLimitType.DAILY -> Pair(now.withTimeAtStartOfDay(), now)
                ExpenseLimitType.WEEKLY -> Pair(getWeekLimitStart(limit.created, now), now)
                ExpenseLimitType.MONTHLY -> Pair(getMonthLimitStart(limit.created, now), now)
            }
    return dates
}

fun getWeekLimitStart(limitStart: DateTime, now: DateTime): DateTime {
    return now.dayOfWeek().setCopy(limitStart.dayOfWeek).withTimeAtStartOfDay()
}

fun getMonthLimitStart(limitStart: DateTime, now: DateTime): DateTime {

    //set the day as beginning
    if (now.dayOfMonth > limitStart.dayOfMonth) {
        return now.dayOfMonth().setCopy(limitStart.dayOfMonth).withTimeAtStartOfDay()
    }

    val previousMonth = now.minusMonths(1)
    if (limitStart.dayOfMonth > previousMonth.dayOfMonth().maximumValue) {
        return now.dayOfMonth().setCopy(1).withTimeAtStartOfDay() //start limit from 1 available day
    }

    return previousMonth.dayOfMonth().setCopy(limitStart.dayOfMonth).withTimeAtStartOfDay()
}