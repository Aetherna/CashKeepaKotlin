package com.aethernadev.cashkeepakotlin.main.activity;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

/**
 * Created by Aetherna on 2016-07-13.
 */
public class MainPresenterTest {

    private final static CurrencyUnit YEN_CURRENCY =  CurrencyUnit.JPY;
    private final static BigDecimal AMOUNT_15 =  BigDecimal.valueOf(15);

    @Mock
    MainInteractor mainInteractor;
    @Mock
    MainUI ui;
    @InjectMocks
    MainPresenter mainPresenter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        mainPresenter.attach(ui);
    }

    @Test
    public void should_display_toast_on_click() {

        mainPresenter.onClickMeh();

        verify(ui).displaySnackBar();
    }

    @Test
    public void should_display_limit_on_load_limit() {
        //having
         when(mainInteractor.getTodayOutstandingLimit()).thenReturn(Money.of(YEN_CURRENCY, AMOUNT_15));

        //when
        mainPresenter.loadLimit();

        //then
        verify(ui).displayOutstandingLimit(YEN_CURRENCY.getCode(), AMOUNT_15);
    }
}
