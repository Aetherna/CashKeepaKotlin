package com.aethernadev.cashkeepakotlin.base

import rx.Observable
import rx.Single

/**
 * Created by Aetherna on 2016-07-20.
 */
abstract class BaseInteractor(val schedulers: SchedulersWrapper) {

    open fun <T> wrapObservableOperation(operation: () -> T): Observable<T> {
        return Observable.just(operation())
                .subscribeOn(schedulers.ioScheduler)
                .observeOn(schedulers.uiScheduler)
    }
}