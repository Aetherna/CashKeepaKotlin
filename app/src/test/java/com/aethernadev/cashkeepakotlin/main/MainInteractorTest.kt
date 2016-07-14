package com.aethernadev.cashkeepakotlin.main

import android.content.SharedPreferences
import com.aethernadev.cashkeepakotlin.CKApp
import com.google.common.truth.Truth
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

/**
 * Created by Aetherna on 2016-07-15.
 */
class MainInteractorTest {

    val sharedPreferences: SharedPreferences = mock()
    val mainInteractor: MainInteractor = MainInteractor(sharedPreferences);

    @Test
    fun should_return_app_not_configured_if_empty_prefs_file() {
        //having
        whenever(sharedPreferences.getBoolean(CKApp.isAppConfiguredKey, false)).thenReturn(false)
        //then
        Truth.assertThat(mainInteractor.isAppConfigured()).isFalse()
    }

    @Test
    fun should_return_app_configured_if_prefs_file_has_config_info() {

        //having
        whenever(sharedPreferences.getBoolean(CKApp.isAppConfiguredKey, false)).thenReturn(true)

        //then
        Truth.assertThat(mainInteractor.isAppConfigured()).isTrue()
    }

}