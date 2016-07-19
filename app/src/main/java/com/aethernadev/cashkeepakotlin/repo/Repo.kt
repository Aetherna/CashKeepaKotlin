package com.aethernadev.cashkeepakotlin.repo

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpanseCategory
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimit
import io.realm.Realm
import io.realm.Sort
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

/**
 * Created by Aetherna on 2016-07-13.
 */

interface Repo {
    fun getTodayOutstandingLimit(): Money

    fun saveLimit(limit: Limit)

    fun saveCategories(chosenCategories: List<Category>)

    fun getCategories(): List<Category>
}

class CashKeepaRepo(val realm: Realm) : Repo {

    override fun saveLimit(limit: Limit) {
        realm.executeTransaction {
            mapExpenseLimit(realm, limit)
        }
    }

    override fun getTodayOutstandingLimit(): Money {
        val limit: ExpenseLimit? = realm.where(ExpenseLimit::class.java).findAllSorted("created", Sort.DESCENDING).first()
        return Money.of(CurrencyUnit.of(limit?.currency), BigDecimal.valueOf(limit?.amount!!.toLong()))
    }

    override fun saveCategories(chosenCategories: List<Category>) {
        realm.executeTransaction {
            realm.delete(ExpanseCategory::class.java)
        }
        chosenCategories.forEach { category ->
            realm.executeTransaction {
                mapCategory(realm, category)
            }
        }
    }

    override fun getCategories(): List<Category> {
        val result = realm.where(ExpanseCategory::class.java).findAll()
        return result.map { expenseCategory -> Category.valueOf(expenseCategory.categoryName!!) }
    }


}