package com.aethernadev.cashkeepakotlin.base

import rx.Observable

/**
 * Created by Aetherna on 2016-07-20.
 */
abstract class BaseInteractor(val schedulers: SchedulersWrapper) {

    open fun <T> wrapAsJustObservable(operation: () -> T?): Observable<T> {
        val value = operation()
        if (value != null) {
            return Observable.just(value)
                    .subscribeOn(schedulers.ioScheduler)
                    .observeOn(schedulers.uiScheduler)
        }
        return Observable.empty()
    }

    open fun <T> wrapAsListObservable(operation: () -> List<T>): Observable<T> {
        return Observable.from(operation())
                .subscribeOn(schedulers.ioScheduler)
                .observeOn(schedulers.uiScheduler)
    }
}