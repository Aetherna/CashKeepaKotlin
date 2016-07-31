package com.aethernadev.cashkeepakotlin.models

import org.joda.money.Money
import org.joda.time.DateTime

/**
 * Created by Aetherna on 2016-07-14.
 */

enum class ExpenseLimitType {
    DAILY, WEEKLY, MONTHLY
}

open class Limit(var created: DateTime = DateTime.now(), var amount: Money, var type: ExpenseLimitType) {
}

data class Expense(var created: DateTime = DateTime.now(), var amount: Money, val category: Category) { //todo add type
}

enum class Category {
    FOOD, TRANSPORT, CLOTHING, UTILITIES, ENTERTAINMENT, MISCELLANEOUS
}
