package com.aethernadev.cashkeepakotlin.repo

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseCategoryRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimitRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseRealm
import io.realm.Realm
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.joda.time.DateTime
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-18.
 */

fun mapExpenseLimitToRealm(realm: Realm, expenseLimit: Limit): ExpenseLimitRealm? {
    val realmLimitRealm: ExpenseLimitRealm? = realm.createObject(ExpenseLimitRealm::class.java)
    realmLimitRealm?.created = expenseLimit.created.millis
    realmLimitRealm?.amount = expenseLimit.amount.amount.toLong().toString()
    realmLimitRealm?.currency = expenseLimit.amount.currencyUnit.currencyCode
    realmLimitRealm?.type = expenseLimit.type.name
    return realmLimitRealm

}

fun mapLimitFromRealm(expenseLimitRealm: ExpenseLimitRealm?): Limit {

    if (expenseLimitRealm == null) {
        throw RuntimeException("Limit can not be null")
    }

    val amount: Money = Money.of(CurrencyUnit.of(expenseLimitRealm.currency), BigDecimal.valueOf(expenseLimitRealm.amount!!.toLong()))
    return Limit(amount, ExpenseLimitType.valueOf(expenseLimitRealm.type!!))
}

fun mapCategoryToRealm(realm: Realm, category: Category): ExpenseCategoryRealm? {
    val realmCategoryRealm: ExpenseCategoryRealm? = realm.createObject(ExpenseCategoryRealm::class.java)
    realmCategoryRealm?.categoryName = category.name
    return realmCategoryRealm
}

fun mapExpenseFromRealm(expenseRealm: ExpenseRealm): Expense {

    val created = DateTime(expenseRealm.created)
    val amount = Money.parse(expenseRealm.amount)

    return Expense(created, amount)

}

fun mapExpenseToRealm(realm: Realm, expense: Expense): ExpenseRealm? {
    val expenseRealm: ExpenseRealm? = realm.createObject(ExpenseRealm::class.java)
    expenseRealm?.amount = expense.amount.toString()
    expenseRealm?.created = expense.created.millis
    return expenseRealm
}