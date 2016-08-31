package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.config.AppConfig
import com.aethernadev.cashkeepakotlin.config.CurrencyConfig
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.nhaarman.mockito_kotlin.mock
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Before
import org.junit.Ignore
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
    internal var mockCurrencyConfigConfig: CurrencyConfig = mock()
    internal var mockAppConfig: AppConfig = AppConfig(mockCurrencyConfigConfig)
    internal var homePresenter: HomePresenter = HomePresenter(mockHomeInteractor, mockAppConfig)

    @Before
    fun setup() {
        homePresenter.attach(mockUi)
    }

    @Ignore //todo REPAIR!
    @Test
    fun should_display_toast_on_click() {

        //having

        val categories: List<Category> = mock()
        val currency: CurrencyUnit = CurrencyUnit.AUD
        `when`(mockHomeInteractor.getCategories()).thenReturn(Observable.just(categories))
//        `when`(mockCurrencyConfigConfig.appCurrency).thenReturn(currency)

        //when
        homePresenter.onAddExpenseClick()

        //then
        verify<HomeUI>(mockUi).displayAddExpenseDialog(categories, currency)
    }

    @Test
    fun should_display_limit_on_load_limit() {
        //having
        val testSpendings = LimitSpendings(TEST_LIMIT_100, listOf(TEST_EXPENSE_15))
        `when`(mockHomeInteractor.getTodayOutstandingLimit()).thenReturn(Observable.just(testSpendings))
        //when
        homePresenter.loadLimit()

        //then
        verify<HomeUI>(mockUi).displayAvailableAndSpent(AMOUNT_85, YEN_CURRENCY.currencyCode, AMOUNT_15)
    }

//    @Test //TODO settings is a dependency. |Shouldn't be done via interactor ?
//    fun should_add_expense(){
//
//        //having
//        val amount = "23.45"
//        val category = Category.CLOTHING
//        `when`(mockSettingsInteractor.getSettings()).thenReturn(Observable.just(null))
//
//        //when
//        homePresenter.addExpense(amount, category)
//
//        //then
//        verify(mockHomeInteractor).addExpense(Expense(amount = Money.of()))
//    }

    companion object {

        private val YEN_CURRENCY = CurrencyUnit.JPY
        private val AMOUNT_15 = BigDecimal.valueOf(15)
        private val AMOUNT_85 = BigDecimal.valueOf(85)
        private val LIMIT_100 = BigDecimal.valueOf(100)
        private val TEST_EXPENSE_15 = Expense(amount = Money.of(YEN_CURRENCY, AMOUNT_15), category = Category.CLOTHING)
        private val TEST_LIMIT_100 = Limit(amount = Money.of(YEN_CURRENCY, LIMIT_100), type = ExpenseLimitType.DAILY)

    }
}
