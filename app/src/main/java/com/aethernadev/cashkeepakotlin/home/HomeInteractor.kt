package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo) {

    open fun getTodayOutstandingLimit(): Money {
        return repo.getTodayOutstandingLimit()
    }

    open fun getCategories(): List<Category> {
        return repo.getCategories()
    }
}