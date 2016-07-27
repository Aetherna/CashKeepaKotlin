package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.models.Category
import com.nhaarman.mockito_kotlin.mock
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import rx.Observable
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class HomePresenterTest {

    internal var mockHomeInteractor: HomeInteractor = mock()
    internal var mockUi: HomeUI = mock()
    internal var homePresenter: HomePresenter = HomePresenter(mockHomeInteractor)

    @Before
    fun setup() {
        homePresenter.attach(mockUi)
    }

    @Test
    fun should_display_toast_on_click() {

        val categories: List<Category> = mock()
        `when`(mockHomeInteractor.getCategories()).thenReturn(Observable.just(categories))

        //when
        homePresenter.onAddExpenseClick()

        //then
        verify<HomeUI>(mockUi).displayAddExpenseDialog(categories)
    }

    @Test
    fun should_display_limit_on_load_limit() {
        //having
        `when`(mockHomeInteractor.getTodayOutstandingLimit()).thenReturn(Observable.just(Money.of(YEN_CURRENCY, AMOUNT_15)))

        //when
        homePresenter.loadLimit()

        //then
        verify<HomeUI>(mockUi).displayOutstandingLimit(YEN_CURRENCY.code, AMOUNT_15)
    }

    companion object {

        private val YEN_CURRENCY = CurrencyUnit.JPY
        private val AMOUNT_15 = BigDecimal.valueOf(15)
    }
}
