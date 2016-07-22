package com.aethernadev.cashkeepakotlin


import android.R
import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.find


fun View.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
    return snack
}

fun View.snackbar(text: Int, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
    return snack
}

fun Activity.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar =
        find<View>(R.id.content).snackbar(text, duration, init)

fun Activity.snackbar(text: Int, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar =
        find<View>(R.id.content).snackbar(text, duration, init)

fun Fragment.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar? =
        getView()?.snackbar(text, duration, init)

fun Fragment.snackbar(text: Int, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar? =
        getView()?.snackbar(text, duration, init)

fun Fragment.text(text: Int): CharSequence? = resources.getText(text)
fun Activity.color(colorRes: Int): Int = resources.getColor(colorRes)
fun Fragment.color(colorRes: Int): Int = resources.getColor(colorRes)

fun EditText.textWatcher(beforeTextChanged: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { s, start, before, count -> },
                         onTextChanged: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit = { s, start, before, count -> },
                         afterTextChange: (s: Editable?) -> Unit = { s -> }) {
    this.addTextChangedListener(object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s, start, before, count)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged(s, start, count, after)
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChange(s)
        }

    })
}