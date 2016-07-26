package com.aethernadev.cashkeepakotlin.models

import org.joda.money.Money
import org.joda.time.DateTime

/**
 * Created by Aetherna on 2016-07-14.
 */

enum class ExpenseLimitType {
    DAILY, WEEKLY, MONTHLY
}

data class Limit(var amount: Money, var type: ExpenseLimitType) {
    var created: DateTime = DateTime.now()
}

data class Expense(var created: DateTime, var amount: Money) { //todo add type
}

enum class Category {
    FOOD, TRANSPORT, CLOTHING, UTILITIES, ENTERTAINMENT, MISCELLANEOUS
}
