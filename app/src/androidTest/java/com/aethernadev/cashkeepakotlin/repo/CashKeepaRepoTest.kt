package com.aethernadev.cashkeepakotlin.repo

import android.support.test.runner.AndroidJUnit4
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseCategoryRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimitRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseRealm
import com.google.common.truth.Truth.assertThat
import io.realm.Realm
import io.realm.RealmConfiguration
import junit.framework.TestCase
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class CashKeepaRepoTest : TestCase() {

    var realm: Realm? = null

    var repo: CashKeepaRepo? = null

    val TEST_MONEY_1: Money = Money.of(CurrencyUnit.EUR, BigDecimal.TEN)
    val TEST_MONEY_2: Money = Money.of(CurrencyUnit.USD, BigDecimal.ONE)
    val TEST_MONEY_3: Money = Money.of(CurrencyUnit.JPY, BigDecimal.valueOf(15))
    val TEST_MONEY_4: Money = Money.of(CurrencyUnit.CAD, BigDecimal.valueOf(7))


    @Rule @JvmField
    var testFolder = TemporaryFolder()


    @Before
    fun before() {

        val tempFolder = testFolder.newFolder("testRealm")
        realm = Realm.getInstance(RealmConfiguration.Builder(tempFolder).build())
        repo = CashKeepaRepo(realm!!)
    }

    @Test
    fun testSaveLimit() {
        //when
        assertThat(realm?.where(ExpenseLimitRealm::class.java)?.count()).isEqualTo(0L)

        repo?.saveLimit(Limit(TEST_MONEY_1, ExpenseLimitType.DAILY))
        //then
        assertThat(realm?.where(ExpenseLimitRealm::class.java)?.count()).isEqualTo(1L)
    }

    @Test
    fun testGetNewestLimit() {
        //having
        repo?.saveLimit(Limit(TEST_MONEY_1, ExpenseLimitType.DAILY))
        repo?.saveLimit(Limit(TEST_MONEY_2, ExpenseLimitType.MONTHLY))
        repo?.saveLimit(Limit(TEST_MONEY_3, ExpenseLimitType.WEEKLY))

        //when
        val limit = repo?.getNewestLimit()
        assertThat(limit).isNotNull()
        assertThat(limit?.amount).isEqualTo(TEST_MONEY_3)
        assertThat(limit?.type).isEqualTo(ExpenseLimitType.WEEKLY)
    }

    @Test
    fun testSaveCategories() {
        //when
        assertThat(realm?.where(ExpenseCategoryRealm::class.java)?.count()).isEqualTo(0L)

        repo?.saveCategories(listOf(Category.CLOTHING, Category.FOOD, Category.TRANSPORT))
        //then
        assertThat(realm?.where(ExpenseCategoryRealm::class.java)?.count()).isEqualTo(3L)
    }

    @Test
    fun testGetCategories() {
        //having
        assertThat(realm?.where(ExpenseCategoryRealm::class.java)?.count()).isEqualTo(0L)
        repo?.saveCategories(listOf(Category.CLOTHING, Category.FOOD, Category.TRANSPORT))

        //when
        val categories = repo?.getCategories()

        assertThat(categories).isNotNull()
        assertThat(categories!!.size).isEqualTo(3)
        assertThat(categories[0]).isEqualTo(Category.CLOTHING)
        assertThat(categories[1]).isEqualTo(Category.FOOD)
        assertThat(categories[2]).isEqualTo(Category.TRANSPORT)
    }

    @Test
    fun testGetExpensesBetween() {

        //having
        val startDate = date(2006, 6, 6)
        val endDate = date(2006, 10, 10)
        //and expenses made after and before it
        val expense1 = Expense(date(2006, 6, 8), TEST_MONEY_1)
        val expense2 = Expense(date(2006, 6, 8), TEST_MONEY_2)
        val earlierExpense = Expense(date(2006, 3, 7), TEST_MONEY_3)
        val laterExpense = Expense(date(2006, 12, 12), TEST_MONEY_4)

        repo?.saveExpense(earlierExpense)
        repo?.saveExpense(expense1)
        repo?.saveExpense(expense2)
        repo?.saveExpense(laterExpense)
        //when
        val expenses = repo?.getExpensesBetween(startDate, endDate)

        assertThat(expenses).isNotNull()

        assertThat(expenses!!.size).isEqualTo(2)
        assertThat(expenses[0]).isEqualTo(expense1)
        assertThat(expenses[1]).isEqualTo(expense2)
    }

    @Test
    fun testSaveExpense() {
        //when
        assertThat(realm?.where(ExpenseRealm::class.java)?.count()).isEqualTo(0L)

        repo?.saveExpense(Expense(date(2006, 6, 7), TEST_MONEY_1))
        repo?.saveExpense(Expense(date(2006, 6, 4), TEST_MONEY_3))
        repo?.saveExpense(Expense(date(2006, 6, 2), TEST_MONEY_2))
        //then
        assertThat(realm?.where(ExpenseRealm::class.java)?.count()).isEqualTo(3L)
    }

    fun date(year: Int, month: Int, dayOfMonth: Int): DateTime {
        return DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(dayOfMonth)
    }

    fun createLimit(created: DateTime, money: Money, type: ExpenseLimitType) {
        val testLimit = Limit(money, type)
        testLimit.created = created
    }
}