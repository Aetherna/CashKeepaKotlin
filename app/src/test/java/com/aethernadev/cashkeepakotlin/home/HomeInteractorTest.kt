package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Test
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class HomeInteractorTest {

    val TEST_MONI = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(200))
    val repo: Repo = mock()
    val schedulers: SchedulersWrapper = SchedulersWrapper(ioScheduler = Schedulers.immediate(), uiScheduler = Schedulers.immediate())
    val homeInteractor: HomeInteractor = HomeInteractor(repo, schedulers)

    @Test
    fun testGetTodayOutstandingLimit() {
        //having
        whenever(repo.getTodayOutstandingLimit()).thenReturn(TEST_MONI)

        //when
        val result = homeInteractor.getTodayOutstandingLimit()

        //then
        assertThat(result).isEqualTo(TEST_MONI)
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
}