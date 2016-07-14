package com.aethernadev.cashkeepakotlin

import android.test.ApplicationTestCase
import com.aethernadev.cashkeepakotlin.home.HomeInteractor
import com.aethernadev.cashkeepakotlin.home.HomePresenter
import com.aethernadev.cashkeepakotlin.main.MainInteractor
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
    val homeInteractor: HomeInteractor by injector.instance()
    val homePresenter: HomePresenter by injector.instance()

    val mainInteractor: MainInteractor by injector.instance()

    @Test
    fun testGetKodein() {
        //having
        createApplication()
        //when
        injector.inject(application.kodein)
        //then
        Assert.assertNotNull(repo)
        Assert.assertNotNull(homeInteractor)
        Assert.assertNotNull(homeInteractor.repo)
        Assert.assertNotNull(homePresenter)
        Assert.assertNotNull(homePresenter.interactor)
        Assert.assertNotNull(homePresenter.interactor.repo)

        Assert.assertNotNull(mainInteractor.sharedPreferences)
    }
}