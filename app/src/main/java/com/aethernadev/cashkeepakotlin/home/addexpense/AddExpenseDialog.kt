package com.aethernadev.cashkeepakotlin.home.addexpense

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.clearErrorOnTextChange
import com.aethernadev.cashkeepakotlin.models.Category
import nz.bradcampbell.paperparcel.PaperParcel
import nz.bradcampbell.paperparcel.PaperParcelable
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick
import org.joda.money.CurrencyUnit

class AddExpenseDialogFragment() : DialogFragment() {

    var dialogConfiguration: AddExpenseConfiguration? = null
    var amountInput: EditText? = null
    var amountError: TextInputLayout? = null
    var amountCurrency: TextView? = null

    companion object {
        fun newInstance(categories: List<Category>, currencyUnit: CurrencyUnit): AddExpenseDialogFragment {
            val expenseConfig: AddExpenseConfiguration = AddExpenseConfiguration(categories, currencyUnit) //todo move it up to the presenter
            val fragment = AddExpenseDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable("config", expenseConfig)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dialogConfiguration = it.getParcelable<AddExpenseConfiguration>("config")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_add_expense, container, false)
        view?.find<GridView>(R.id.add_expense_categories)?.adapter = CategoriesAdapter(context, dialogConfiguration?.categories)
        amountError = view?.find<TextInputLayout>(R.id.add_expense_amount_input_error)
        amountInput = view?.find<EditText>(R.id.add_expense_amount_input)
        amountInput?.clearErrorOnTextChange(amountError)
        amountCurrency = view?.find<TextView>(R.id.add_expense_amount_currency)
        amountCurrency?.text = dialogConfiguration?.currencyUnit?.currencyCode

        return view
    }

    fun onCategorySelected(category: Category): Boolean {
        if (amountInput?.text.isNullOrBlank()) {
            amountError?.error = "Enter spent" //todo
            return false
        }
        val amount = amountInput?.text.toString()
        (targetFragment as AddExpenseListener).onExpenseAdded(amount, category)
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
data class AddExpenseConfiguration(val categories: List<Category>, val currencyUnit: CurrencyUnit) : PaperParcelable {
    companion object {
        @JvmField val CREATOR = PaperParcelable.Creator(AddExpenseConfiguration::class.java)
    }
}

interface AddExpenseListener {
    fun onExpenseAdded(amount: String, category: Category)
}
