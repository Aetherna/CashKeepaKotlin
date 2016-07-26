package com.aethernadev.cashkeepakotlin.repo

import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.models.Expense
import com.aethernadev.cashkeepakotlin.models.Limit
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseCategoryRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseLimitRealm
import com.aethernadev.cashkeepakotlin.repo.dbmodels.ExpenseRealm
import io.realm.Realm
import io.realm.Sort
import org.joda.time.DateTime

/**
 * Created by Aetherna on 2016-07-13.
 */

interface Repo {
    fun getNewestLimit(): Limit

    fun saveLimit(limit: Limit)

    fun saveCategories(chosenCategories: List<Category>)

    fun getCategories(): List<Category>

    fun getExpensesBetween(startDate: DateTime, endDate: DateTime): List<Expense>

    fun saveExpense(expense: Expense)
}

class CashKeepaRepo(val realm: Realm) : Repo {

    override fun saveLimit(limit: Limit) {
        realm.executeTransaction {
            mapExpenseLimitToRealm(realm, limit)
        }
    }

    override fun getNewestLimit(): Limit {
        val limitRealm: ExpenseLimitRealm? = realm.where(ExpenseLimitRealm::class.java).findAllSorted("created", Sort.DESCENDING).first()
        return mapLimitFromRealm(limitRealm)
    }

    override fun saveCategories(chosenCategories: List<Category>) {
        realm.executeTransaction {
            realm.delete(ExpenseCategoryRealm::class.java)
        }
        chosenCategories.forEach { category ->
            realm.executeTransaction {
                mapCategoryToRealm(realm, category)
            }
        }
    }

    override fun getCategories(): List<Category> {
        val result = realm.where(ExpenseCategoryRealm::class.java).findAll()
        return result.map { expenseCategory -> Category.valueOf(expenseCategory.categoryName!!) }
    }

    override fun getExpensesBetween(startDate: DateTime, endDate: DateTime): List<Expense> {
        val expenses = realm.where(ExpenseRealm::class.java)
                .greaterThan("created", startDate.millis)
                .lessThan("created", endDate.millis)
                .findAll()

        if (expenses.isEmpty()) {
            return listOf()
        }

        return expenses.map { mapExpenseFromRealm(it) }.toList()
    }

    override fun saveExpense(expense: Expense) {
        realm.executeTransaction {
            mapExpenseToRealm(realm, expense)
        }
    }


}