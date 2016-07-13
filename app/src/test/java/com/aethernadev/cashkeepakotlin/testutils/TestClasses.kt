package com.aethernadev.cashkeepakotlin.testutils

import com.aethernadev.cashkeepakotlin.base.BasePresenter

/**
 * Created by Aetherna on 2016-07-13.
 */
interface TestUI {
    fun presentDataOnUI()
}

open class TestPresenter : BasePresenter<TestUI>() {
}

open class TestUIScreen : TestUI {
    override fun presentDataOnUI() {
        throw UnsupportedOperationException()
    }
}