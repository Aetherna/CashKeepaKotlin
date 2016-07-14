package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo) {

    open fun getTodayOutstandingLimit(): Money {
        return repo.getTodayOutstandingLimit()
    }
}