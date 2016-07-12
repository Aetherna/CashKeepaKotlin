package com.aethernadev.cashkeepakotlin.base

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock

/**
 * Created by Aetherna on 2016-07-13.
 */
class BasePresenterTest {

    val UI_ACTION = { ui: TestUI? -> ui?.presentDataOnUI() }
    @Mock
    var ui: TestUIScreen = mock()
    var presenter: TestPresenter = TestPresenter()


    @Test
    fun should_store_ui_action_in_queue_when_ui_not_attached() {

        //when
        presenter.presentOn(UI_ACTION)

        //that
        assertThat(presenter.pendingUIActions).contains(UI_ACTION)
    }

    @Test
    fun should_launch_queued_ui_actions_in_queue_when_ui_attached() {
        //having
        presenter.presentOn(UI_ACTION)
        presenter.presentOn(UI_ACTION)

        //when
        presenter.attach(ui)
        //that
        verify(ui, times(2)).presentDataOnUI()
    }

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
}