package com.aethernadev.cashkeepakotlin


import android.R
import android.app.Activity

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
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