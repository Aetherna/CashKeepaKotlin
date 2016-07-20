package com.aethernadev.cashkeepakotlin.home.addexpense

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.models.Category
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater

class AddExpenseDialogFragment : DialogFragment() {

    var categories: List<Category>? = null

    companion object {
        fun newInstance(categories: List<Category>): AddExpenseDialogFragment {
            val fragment = AddExpenseDialogFragment()
            val bundle = Bundle()
            bundle.putStringArray("categories", categories.map { it.name }.toTypedArray())
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoriesNames: Array<String>? = savedInstanceState?.getStringArray("categories")
        if (categoriesNames == null || categoriesNames.isEmpty()) {
            categories = listOf(Category.FOOD, Category.CLOTHING)
        } else {
            categories = categoriesNames.map { Category.valueOf(it) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialog_add_expense, container, false)
        view?.find<GridView>(R.id.add_expense_categories)?.adapter = CategoriesAdapter(context, categories)
        return view
    }

    inner class CategoriesAdapter(context: Context, val data: List<Category>?) : ArrayAdapter<Category>(context, R.layout.setup_category_item) {


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView ?: context.layoutInflater.inflate(R.layout.setup_category_item, parent, false) //todo
            if (data != null) {
                val resources: Pair<String, Int> = getCategoryResource(data[position])
                view.find<TextView>(R.id.setup_category_name).text = resources.first
                view.find<ImageView>(R.id.setup_category_icon).imageResource = resources.second
            }
            return view
        }

        override fun getCount(): Int {
            return if (data != null) data.size else 0
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
//TODO
//class Wrapper : Parcelable{
//    override fun writeToParcel(dest: Parcel?, flags: Int) {
//        dest?.writeStringList(listOf("a"))
//    }
//
//    override fun describeContents(): Int {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//
//
//          val CREATOR: Parcelable.Creator<ScriptureReference> = object : Parcelable.Creator<ScriptureReference> {
//            override fun createFromParcel(parcelIn: Parcel): ScriptureReference {
//                return ScriptureReference(parcelIn)
//            }
//
//            override fun newArray(size: Int): Array<ScriptureReference> {
//                return Array(size, {i -> ScriptureReference()})
//            }
//        }
//
//
//}



