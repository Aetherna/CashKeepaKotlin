package com.aethernadev.cashkeepakotlin.home.addexpense

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.models.Category
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal

class AddExpenseDialogFragment() : DialogFragment() {

    var categories: List<Category>? = null
    var amountInput: EditText? = null
    var callback: AddExpenseListener? = null
    var amountError: TextInputLayout? = null

    companion object {
        fun newInstance(categories: List<Category>): AddExpenseDialogFragment {
            val expenseConfig: AddExpenseConfiguration = AddExpenseConfiguration(categories) //todo move it up to the presenter
            val fragment = AddExpenseDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable("categories", expenseConfig)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categories = it.getParcelable<AddExpenseConfiguration>("categories")?.categories
        }
//        callback = targetFragment as AddExpenseListener

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = activity.layoutInflater?.inflate(R.layout.dialog_add_expense, null, false)
        view?.find<GridView>(R.id.add_expense_categories)?.adapter = CategoriesAdapter(context, categories)

        return AlertDialog.Builder(activity)
                .setTitle("title")
//                            .setView(view)
                .create()
    }

//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater?.inflate(R.layout.dialog_add_expense, container, false)
//        view?.find<GridView>(R.id.add_expense_categories)?.adapter = CategoriesAdapter(context, categories)
//        amountError = view?.find<TextInputLayout>(R.id.add_expense_amount_input_error)
//        amountInput = view?.find<EditText>(R.id.add_expense_amount_input)
//        amountInput?.clearErrorOnTextChange(amountError)
//
//        return view
//    }

    fun onCategorySelected(category: Category): Boolean {
        if (amountInput?.text.isNullOrBlank()) {
            amountError?.error = "Enter amount"
            return false
        }
        val amount = amountInput?.text.toString().toLong()
        callback?.onExpenseAdded(ExpenseAddedData(Money.of(CurrencyUnit.USD, BigDecimal.valueOf(amount)), category))
        return true
    }

    inner class CategoriesAdapter(context: Context, data: List<Category>?) : ArrayAdapter<Category>(context, R.layout.setup_category_item, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: context.layoutInflater.inflate(R.layout.add_expense_dialog_category, parent, false) //todo view holder
            val resources: Pair<String, Int> = getCategoryResource(getItem(position))
            view.find<TextView>(R.id.add_expense_category_name).text = resources.first
            view.find<ImageView>(R.id.add_expense_category_icon).imageResource = resources.second
            view.find<ImageView>(R.id.add_expense_category_icon).onClick {
                if (onCategorySelected(getItem(position))) {
                    dismiss()
                }
            }
            return view
        }
    }
}

fun getCategoryResource(category: Category): Pair<String, Int> {
    return when (category) {
        Category.CLOTHING -> Pair(category.name, R.drawable.shirt)
        Category.FOOD -> Pair(category.name, R.drawable.burger)
        Category.ENTERTAINMENT -> Pair(category.name, R.drawable.game_console)
        Category.MISCELLANEOUS -> Pair(category.name, R.drawable.credit_card)
        Category.UTILITIES -> Pair(category.name, R.drawable.house)
        Category.TRANSPORT -> Pair(category.name, R.drawable.ufo)
    }
}

@PaperParcel
data class AddExpenseConfiguration(val categories: List<Category>) : PaperParcelable {
    //todo add currency!
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(AddExpenseConfiguration::class.java)
    }
}

interface AddExpenseListener {
    fun onExpenseAdded(expense: ExpenseAddedData)
}

data class ExpenseAddedData(val amount: Money, val category: Category) {
}




