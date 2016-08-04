package com.aethernadev.cashkeepakotlin.repo

import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimitRealm
import com.google.common.truth.Truth
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.time.DateTime
import org.junit.Test
import java.math.BigDecimal

class MappersKtTest {

    @Test
    fun testMapLimitFromRealm() {

        //having

        val testMoney = Money.of(CurrencyUnit.AUD, BigDecimal.valueOf(23.34))
        val testLimitCreatedDate = DateTime(2015, 4, 2, 7, 30)
        val testType = ExpenseLimitType.DAILY

        val realmLimit = ExpenseLimitRealm()
        realmLimit.created = testLimitCreatedDate.millis
        realmLimit.amountMinor = testMoney.amountMinorLong
        realmLimit.currencyCode = testMoney.currencyUnit.currencyCode
        realmLimit.type = testType.name

        //when
        val result = mapLimitFromRealm(realmLimit)

        //then
        Truth.assertThat(result.created).isEqualTo(testLimitCreatedDate)
        Truth.assertThat(result.amount).isEqualTo(testMoney)
        Truth.assertThat(result.type).isEqualTo(testType)

    }
}