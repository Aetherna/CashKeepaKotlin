package com.aethernadev.cashkeepakotlin.models

import org.joda.money.Money
import org.joda.time.DateTime

/**
 * Created by Aetherna on 2016-07-14.
 */

enum class ExpenseLimitType {
    DAILY, WEEKLY, MONTHLY
}

data class Limit(var created: DateTime, var amount: Money, var type: ExpenseLimitType) {
}

data class Expense(var created: DateTime, var amount: Money) { //todo add type
}

enum class ExpenseCategory {
    FOOD, ENTERTAINMENT, TRANSPORT, UTILITIES, CLOTHING, MISCELLANEOUS
}
