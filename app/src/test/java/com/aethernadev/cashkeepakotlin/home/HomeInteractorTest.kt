package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.time.DateTime
import org.junit.Ignore
import org.junit.Test
import org.mockito.Matchers.anyObject
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class HomeInteractorTest {

    val TEST_MONI = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(200))
    val TEST_LIMIT_MONI = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(1000))
    val TEST_EXPENSE = Expense(DateTime.now(), TEST_MONI, Category.CLOTHING)
    val repo: Repo = mock()
    val schedulers: SchedulersWrapper = SchedulersWrapper(ioScheduler = Schedulers.immediate(), uiScheduler = Schedulers.immediate())
    val homeInteractor: HomeInteractor = HomeInteractor(repo, schedulers)

    @Ignore //todo fix it
    @Test
    fun testNewestLimit() {
        //having
        val testLimit: Limit = Limit(amount = TEST_LIMIT_MONI, type = ExpenseLimitType.DAILY)
        val expenses = listOf(TEST_EXPENSE, TEST_EXPENSE)

        whenever(repo.getNewestLimit()).thenReturn(testLimit)
        whenever(repo.getExpensesBetween(anyObject(), anyObject())).thenReturn(expenses)

        val testSubscriber: TestSubscriber<LimitSpendings> = TestSubscriber()

        //when
        val result = homeInteractor.getTodayOutstandingLimit().subscribe(testSubscriber)

        //then
        testSubscriber.assertNoErrors()
        testSubscriber.assertReceivedOnNext(mutableListOf(LimitSpendings(testLimit,expenses)))
        assertThat(result).isEqualTo(testLimit)
    }

    @Test
    fun testCategories() {
        //having
        whenever(repo.getCategories()).thenReturn(listOf(Category.FOOD, Category.CLOTHING))
        val testSubscriber: TestSubscriber<List<Category>> = TestSubscriber()

        //when
        homeInteractor.getCategories().subscribe(testSubscriber)

        //then
        testSubscriber.assertNoErrors()
        testSubscriber.assertReceivedOnNext(mutableListOf(listOf(Category.FOOD, Category.CLOTHING)))

    }

    @Test
    fun testAddExpense() {
        //having
        val testSubscriber: TestSubscriber<Unit> = TestSubscriber()

        //when
        homeInteractor.addExpense(TEST_EXPENSE).subscribe(testSubscriber)

        //then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
    }

    @Test
    fun testLimitDatesDaily() {

        //having
        val now = DateTime(2016, 6, 6, 12, 30)
        val limit = getLimit(2016, 4, 4, ExpenseLimitType.DAILY)

        //when
        val limitPeriod = limitDates(limit, now)

        //then
        assertThat(limitPeriod.first).isEqualTo(now.withTimeAtStartOfDay())
        assertThat(limitPeriod.second).isEqualTo(now)
    }

    @Test
    fun testLimitDatesWeekly() {

        //having now is  2016/6/7 -> Tuesday
        val now = DateTime(2016, 6, 7, 12, 30)
        //limit was setup as starting in 2016/4/4 on Monday
        val limit = getLimit(2016, 4, 4, ExpenseLimitType.WEEKLY)

        //when
        val limitPeriod = limitDates(limit, now)

        //then assert this accounting period starts from monday
        assertThat(limitPeriod.first).isEqualTo(DateTime(2016, 6, 6, 0, 0))
        assertThat(limitPeriod.second).isEqualTo(now)
    }

    @Test
    fun testLimitDatesMonthDayAfterLimit() {

        //having now dayOfMonth is after limit dayOfMonth
        val now = DateTime(2016, 6, 10, 12, 30)
        //limit
        val limit = getLimit(2016, 4, 4, ExpenseLimitType.MONTHLY)

        //when
        val limitPeriod = limitDates(limit, now)

        //then assert our accounting period starts this month at 4th
        assertThat(limitPeriod.first).isEqualTo(DateTime(2016, 6, 4, 0, 0))
        assertThat(limitPeriod.second).isEqualTo(now)
    }

    @Test
    fun testLimitDatesMonthDayFebruary() {

        //having now as march
        val now = DateTime(2016, 3, 2, 12, 30)
        //asn limit set on 31th of January
        val limit = getLimit(2016, 1, 31, ExpenseLimitType.MONTHLY)

        //when
        val limitPeriod = limitDates(limit, now)

        //then we assert our counting period  NOT as 31 february (doh) but 1st march
        assertThat(limitPeriod.first).isEqualTo(DateTime(2016, 3, 1, 0, 0))
        assertThat(limitPeriod.second).isEqualTo(now)
    }

    @Test
    fun testLimitDatesMonthGoBack() {

        //having now as march
        val now = DateTime(2016, 3, 15, 12, 30)
        //asn limit set on dayOfMonth later then dayOfMonth now
        val limit = getLimit(2016, 1, 20, ExpenseLimitType.MONTHLY)

        //when
        val limitPeriod = limitDates(limit, now)

        //then we start accounting period from feb 20
        assertThat(limitPeriod.first).isEqualTo(DateTime(2016, 2, 20, 0, 0))
        assertThat(limitPeriod.second).isEqualTo(now)
    }

    fun getLimit(year: Int, month: Int, day: Int, type: ExpenseLimitType): Limit {
        val start = DateTime(year, month, day, 10, 20)
        return Limit(start, Money.of(CurrencyUnit.USD, BigDecimal.TEN), type)
    }

    fun getLimit(amount: Int): Limit {
        val start = DateTime(2016, 6, 6, 10, 20)
        return Limit(start, Money.of(CurrencyUnit.USD, BigDecimal.valueOf(amount.toDouble())), ExpenseLimitType.DAILY)
    }


    @Test
    fun testTotalSpendings() {
        //having
        val limit = getLimit(100)
        val expenses: List<Expense> = listOf(getExpense(5.75), getExpense(6.toDouble()), getExpense(2.toDouble()))

        //when
        val outstanding = LimitSpendings(limit, expenses).available
        assertThat(outstanding.amount).isEqualTo(BigDecimal.valueOf("86.25".toDouble()))
    }

    fun getExpense(amount: Double): Expense {
        return Expense(amount = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(amount)), category = Category.CLOTHING)
    }
}