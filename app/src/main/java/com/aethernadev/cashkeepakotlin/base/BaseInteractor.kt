package com.aethernadev.cashkeepakotlin.base

import rx.Observable

/**
 * Created by Aetherna on 2016-07-20.
 */
abstract class BaseInteractor(val schedulers: SchedulersWrapper) {

    open fun <T> wrapAsJustObservable(operation: () -> T): Observable<T> {
        return Observable.just(operation())
                .subscribeOn(schedulers.ioScheduler)
                .observeOn(schedulers.uiScheduler)
    }

    open fun <T> wrapAsListObservable(operation: () -> List<T>): Observable<T> {
        return Observable.from(operation())
                .subscribeOn(schedulers.ioScheduler)
                .observeOn(schedulers.uiScheduler)
    }
}