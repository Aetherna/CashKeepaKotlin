package com.aethernadev.cashkeepakotlin.welcome

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.aethernadev.cashkeepakotlin.main.MainActivity
import org.jetbrains.anko.*

/**
 * Created by Aetherna on 2016-07-14.
 */
class WelcomeFragment : Fragment() {

    var configDone: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return UI {
            verticalLayout {
                textView {
                    text = "Welcome"
                }
                configDone = button(text = "Done!") {
                    onClick {
                        (activity as MainActivity).onConfigDone()
                    }
                }
                textView {
                    text = "Welcome"
                }
            }
        }.view
    }
}