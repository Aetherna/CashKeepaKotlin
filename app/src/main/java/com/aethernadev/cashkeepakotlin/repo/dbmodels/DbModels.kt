package com.aethernadev.cashkeepakotlin.repo.dbmodels

import io.realm.RealmObject

/**
 * Created by Aetherna on 2016-07-14.
 */
open class ExpenseRealm : RealmObject() {
    open var created: Long? = null
    open var amountMinor: Long? = null
    open var currencyCode: String? = null
    open var expenseCategory: String? = null
}

open class ExpenseLimitRealm : RealmObject() {

    open var created: Long? = null
    open var amountMinor: Long? = null
    open var currencyCode: String? = null
    open var type: String? = null
}

open class ExpenseCategoryRealm : RealmObject() {
    open var categoryName: String? = null
}
