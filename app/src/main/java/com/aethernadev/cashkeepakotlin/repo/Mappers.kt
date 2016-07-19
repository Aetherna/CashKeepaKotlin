package com.aethernadev.cashkeepakotlin.repo

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpanseCategory
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimit
import io.realm.Realm

/**
 * Created by Aetherna on 2016-07-18.
 */

fun mapExpenseLimit(realm: Realm, expenseLimit: Limit): ExpenseLimit? {
    var realmLimit: ExpenseLimit? = realm.createObject(ExpenseLimit::class.java)
    realmLimit?.created = expenseLimit.created.millis
    realmLimit?.amount = expenseLimit.amount.amount.toLong().toString()
    realmLimit?.currency = expenseLimit.amount.currencyUnit.currencyCode
    realmLimit?.type = expenseLimit.type.name
    return realmLimit

}

fun mapCategory(realm: Realm, category: Category): ExpanseCategory? {
    var realmCategory: ExpanseCategory? = realm.createObject(ExpanseCategory::class.java)
    realmCategory?.categoryName = category.name
    return realmCategory
}