package com.aethernadev.cashkeepakotlin.main.activity

import com.aethernadev.cashkeepakotlin.Repo
import org.joda.money.Money

/**
 * Created by Aetherna on 2016-07-12.
 */
open class MainInteractor(val repo: Repo) {

    open fun getTodayOutstandingLimit(): Money {
        return repo.getTodayOutstandingLimit()
    }
}