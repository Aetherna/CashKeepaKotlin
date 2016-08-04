package com.aethernadev.cashkeepakotlin.setup

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money
import org.joda.time.DateTime

/**
 * Created by Aetherna on 2016-07-16.
 */

class SetupInteractor(val repo: Repo) {

    fun saveLimit(amount: Money, limitType: ExpenseLimitType) {
        repo.saveLimit(Limit(DateTime.now(), amount, limitType))
    }

    fun saveCategories(chosenCategories: List<Category>) {
        repo.saveCategories(chosenCategories)
    }

    fun saveInitialCategories() {
        repo.saveCategories(Category.values().toList())
    }
}