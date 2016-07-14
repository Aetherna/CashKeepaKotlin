package com.aethernadev.cashkeepakotlin.home

import com.nhaarman.mockito_kotlin.mock
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class HomePresenterTest {

    internal var homeInteractor: HomeInteractor = mock()
    internal var ui: HomeUI =   mock()
    internal var homePresenter: HomePresenter =  HomePresenter(homeInteractor)

    @Before
    fun setup() {
        homePresenter.attach(ui)
    }

    @Test
    fun should_display_toast_on_click() {

        homePresenter.onClickMeh()
        verify<HomeUI>(ui).displaySnackBar()
    }

    @Test
    fun should_display_limit_on_load_limit() {
        //having
        `when`(homeInteractor.getTodayOutstandingLimit()).thenReturn(Money.of(YEN_CURRENCY, AMOUNT_15))

        //when
        homePresenter.loadLimit()

        //then
        verify<HomeUI>(ui).displayOutstandingLimit(YEN_CURRENCY.code, AMOUNT_15)
    }

    companion object {

        private val YEN_CURRENCY = CurrencyUnit.JPY
        private val AMOUNT_15 = BigDecimal.valueOf(15)
    }
}
