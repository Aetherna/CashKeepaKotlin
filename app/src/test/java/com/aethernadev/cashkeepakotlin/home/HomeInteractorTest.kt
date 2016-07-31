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

        val testSubscriber: TestSubscriber<Money> = TestSubscriber()

        //when
        val result = homeInteractor.getTodayOutstandingLimit().subscribe(testSubscriber)

        //then
        testSubscriber.assertNoErrors()
        testSubscriber.assertReceivedOnNext(mutableListOf(TEST_MONI))
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
}