package com.aethernadev.cashkeepakotlin.main.activity

import com.nhaarman.mockito_kotlin.mock
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Before
import org.junit.Test

import java.math.BigDecimal

import org.mockito.Mockito.*

/**
 * Created by Aetherna on 2016-07-13.
 */
class MainPresenterTest {


    internal var mainInteractor: MainInteractor = mock()
    internal var ui: MainUI =   mock()
    internal var mainPresenter: MainPresenter =  MainPresenter(mainInteractor)

    @Before
    fun setup() {
        mainPresenter.attach(ui)
    }

    @Test
    fun should_display_toast_on_click() {

        mainPresenter.onClickMeh()
        verify<MainUI>(ui).displaySnackBar()
    }

    @Test
    fun should_display_limit_on_load_limit() {
        //having
        `when`(mainInteractor.getTodayOutstandingLimit()).thenReturn(Money.of(YEN_CURRENCY, AMOUNT_15))

        //when
        mainPresenter.loadLimit()

        //then
        verify<MainUI>(ui).displayOutstandingLimit(YEN_CURRENCY.code, AMOUNT_15)
    }

    companion object {

        private val YEN_CURRENCY = CurrencyUnit.JPY
        private val AMOUNT_15 = BigDecimal.valueOf(15)
    }
}
