package com.aethernadev.cashkeepakotlin.repo.dbmodels

import io.realm.RealmObject

/**
 * Created by Aetherna on 2016-07-14.
 */
open class Expense : RealmObject() {
    open var created: Long? = null
    open var amount: String? = null
}

open class ExpenseLimit : RealmObject() {

    open var created: Long? = null
    open var amount: String? = null
    open var currency: String? = null
    open var type: String? = null
}

open class ExpanseCategory : RealmObject() {
    open var categoryName: String? = null;
}
