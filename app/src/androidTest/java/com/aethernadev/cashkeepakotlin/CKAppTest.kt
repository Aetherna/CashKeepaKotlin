package com.aethernadev.cashkeepakotlin

import android.test.ApplicationTestCase
import com.aethernadev.cashkeepakotlin.main.activity.MainInteractor
import com.aethernadev.cashkeepakotlin.main.activity.MainPresenter
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.github.salomonbrys.kodein.KodeinInjector
import org.junit.Assert
import org.junit.Test

/**
 * Created by Aetherna on 2016-07-14.
 */
class CKAppTest : ApplicationTestCase<CKApp>(CKApp::class.java) {

    val injector = KodeinInjector()
    val repo: Repo by injector.instance()
    val interactor: MainInteractor by injector.instance()
    val presenter: MainPresenter by injector.instance()

    @Test
    fun testGetKodein() {
        //having
        createApplication()
        //when
        injector.inject(application.kodein)
        //then
        Assert.assertNotNull(repo)
        Assert.assertNotNull(interactor)
        Assert.assertNotNull(interactor.repo)
        Assert.assertNotNull(presenter)
        Assert.assertNotNull(presenter.interactor)
        Assert.assertNotNull(presenter.interactor.repo)
    }
}