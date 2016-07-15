package com.aethernadev.cashkeepakotlin.welcome

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.aethernadev.cashkeepakotlin.R
import com.aethernadev.cashkeepakotlin.main.MainActivity
import com.aethernadev.cashkeepakotlin.models.ExpenseCategory
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType
import com.aethernadev.cashkeepakotlin.models.ExpenseLimitType.*
import com.aethernadev.cashkeepakotlin.text
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.pagerTabStrip
import org.jetbrains.anko.support.v4.viewPager
import org.joda.money.CurrencyUnit
import org.joda.money.CurrencyUnit.*
import java.util.*

/**
 * Created by Aetherna on 2016-07-14.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = UI {
            verticalLayout {
                viewPager(theme = R.style.WelcomeStepsPager) {
                    pagerTabStrip(theme = R.style.CKTabStrip) {
                    }
                    id = 2
                    adapter = StepsPagerAdapter(childFragmentManager)
                }
            }
        }.view
        return view
    }

    inner class StepsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        var views = listOf(LimitSetup(), CategoriesSetup())
        var titles = listOf(text(R.string.limit), text(R.string.categories))

        override fun getCount(): Int {
            return views.size
        }

        override fun getItem(position: Int): Fragment? {
            return views[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}

class LimitSetup : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout(theme = R.style.MainScreenContainer) {
                textView {
                    text = text(R.string.add_your_limit)
                }
                linearLayout() {
                    editText {
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    spinner {
                        adapter = ArrayAdapter<CurrencyUnit>(context, R.layout.support_simple_spinner_dropdown_item, getSupportedCurrencies(getResources().getConfiguration().locale))
                    }
                }
                relativeLayout {
                    radioGroup {
                        ExpenseLimitType.values().forEach {
                            radioButton {
                                text = when (it) {
                                    DAILY -> text(R.string.per_day)
                                    WEEKLY -> text(R.string.per_week)
                                    MONTHLY -> text(R.string.per_month)
                                }
                            }
                        }
                    }.apply {
                        orientation = LinearLayout.HORIZONTAL
                        check(1)
                    }
                }
            }
        }.view
    }
}

class CategoriesSetup : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout(theme = R.style.MainScreenContainer) {
                textView {
                    text = text(R.string.select_categories)
                }
                ExpenseCategory.values().forEach {
                    checkBox(text = it.name)
                }
                button(text = text(R.string.done)) {
                    onClick {
                        (activity as MainActivity).onConfigDone() //todo interface
                    }
                }
            }
        }.view
    }
}

fun getSupportedCurrencies(locale: Locale): List<CurrencyUnit> {
    val common = listOf(USD, EUR, GBP)
    val current = listOf(getInstance(locale))

    return current.union(common).union(registeredCurrencies()).toList()
}


