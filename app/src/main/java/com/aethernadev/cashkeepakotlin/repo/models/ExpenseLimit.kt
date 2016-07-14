package com.aethernadev.cashkeepakotlin.repo.models

import io.realm.RealmObject
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */

open class ExpenseLimit : RealmObject() {

    open var amount: String? = null
    open var currency: String? = null
    open var type: String? = null

}

enum class ExpenseLimitType {
    DAILY, WEEKLY, MONTHLY
}