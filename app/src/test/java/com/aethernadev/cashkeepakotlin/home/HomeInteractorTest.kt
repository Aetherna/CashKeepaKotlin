package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.repo.Repo
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.Test
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */
class HomeInteractorTest {

    val TEST_MONI = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(200))
    val repo: Repo = mock()
    val homeInteractor: HomeInteractor = HomeInteractor(repo)

    @Test
    fun testGetTodayOutstandingLimit() {
        //having
        whenever(repo.getTodayOutstandingLimit()).thenReturn(TEST_MONI)

        //when
       val result = homeInteractor.getTodayOutstandingLimit()

        Truth.assertThat(result).isEqualTo(TEST_MONI)
    }
}