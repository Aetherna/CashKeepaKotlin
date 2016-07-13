package com.aethernadev.cashkeepakotlin.main.activity

import com.aethernadev.cashkeepakotlin.Repo
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class MainInteractorTest {

    val TEST_MONI = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(200))
    val repo: Repo = mock()
    val mainInteractor: MainInteractor = MainInteractor(repo);

    @Test
    fun testGetTodayOutstandingLimit() {
        //having
        whenever(repo.getTodayOutstandingLimit()).thenReturn(TEST_MONI)

        //when
       val result = mainInteractor.getTodayOutstandingLimit()

        Truth.assertThat(result).isEqualTo(TEST_MONI)
    }
}