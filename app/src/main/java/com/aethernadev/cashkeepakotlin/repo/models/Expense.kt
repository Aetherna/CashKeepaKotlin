package com.aethernadev.cashkeepakotlin.repo.models

import io.realm.RealmObject
import org.joda.time.DateTime
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-14.
 */
open class Expense : RealmObject(){
    open var date: Long? = null
    open var amount: String? = null
}