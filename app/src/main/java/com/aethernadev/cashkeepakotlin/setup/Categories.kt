package com.aethernadev.cashkeepakotlin.setup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.aethernadev.cashkeepakotlin.CKApp
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.base.BaseFragment
import com.aethernadev.cashkeepakotlin.base.BasePresenter
import com.aethernadev.cashkeepakotlin.main.MainActivity
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.text
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

/**
 * Created by Aetherna on 2016-07-14.
 */

class SetupCategoriesFragment : BaseFragment<SetupCategoriesPresenter, CategoriesUI>(), CategoriesUI {

    val setupPresenterSetup: SetupCategoriesPresenter by injector.instance()
    override fun closeSetup() {
        (activity as MainActivity).onConfigDone()
    }

    override fun getUI(): CategoriesUI {
        return this;
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        injector.inject((activity.application as CKApp).kodein)
        presenter = setupPresenterSetup
        val categoriesAdapter = CategoriesAdapter(context, Category.values().toList())

        return UI {
            verticalLayout(theme = R.style.Match) {
                textView(theme = R.style.HeaderText) {
                    textResource = R.string.select_categories
                }
                listView {
                    adapter = categoriesAdapter
                }.lparams(width = matchParent)
                button(theme = R.style.PrimaryButton, text = text(R.string.done)) {
                    onClick {
                        presenter?.setupDone(categoriesAdapter.selectedCategories)
                    }.apply { gravity = bottom }
                }
            }
        }.view
    }
}

class CategoriesAdapter(context: Context, val data: List<Category>) : ArrayAdapter<Category>(context, R.layout.setup_category_item) {

    var selectedCategories: MutableList<Category> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView ?: context.layoutInflater.inflate(R.layout.setup_category_item, parent, false)
        view.setTag(data[position])

        val resources: Pair<String, Int> = getCategoryResource(data[position])
        view.find<TextView>(R.id.setup_category_name).text = resources.first
        view.find<ImageView>(R.id.setup_category_icon).imageResource = resources.second
        view.find<CheckBox>(R.id.setup_category_check).onCheckedChange { compoundButton, checked ->
            if (checked) {
                selectedCategories.add(view.getTag() as Category)
            } else {
                selectedCategories.remove(view.getTag() as Category)
            }
        }
        return view
    }

    override fun getCount(): Int {
        return data.size
    }
}

fun getCategoryResource(category: Category): Pair<String, Int> {//todo String to resource
    return when (category) {
        Category.CLOTHING -> Pair(category.name, R.drawable.shirt)
        Category.FOOD -> Pair(category.name, R.drawable.burger)
        Category.ENTERTAINMENT -> Pair(category.name, R.drawable.game_console)
        Category.MISCELLANEOUS -> Pair(category.name, R.drawable.credit_card)
        Category.UTILITIES -> Pair(category.name, R.drawable.house)
        Category.TRANSPORT -> Pair(category.name, R.drawable.ufo)
    }
}

class SetupCategoriesPresenter(val interactor: SetupInteractor) : BasePresenter<CategoriesUI>() {
    fun setupDone(chosenCategories: List<Category>) {
        interactor.saveCategories(chosenCategories);
        presentOn { ui: CategoriesUI? -> ui?.closeSetup() }
    }
}

interface CategoriesUI {
    fun closeSetup()
}



