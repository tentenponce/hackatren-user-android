package com.nasaanka.domain.common.base

import com.nasaanka.domain.common.executor.PostExecutionThread
import com.nasaanka.domain.common.executor.ThreadExecutor
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCaseNoParam<Return>(@param:NonNull private val threadExecutor: ThreadExecutor,
                                                @param:NonNull private val postExecutionThread: PostExecutionThread) {

    abstract fun buildObservable(): Observable<Return>

    fun execute(): Observable<Return> {
        return buildObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
    }
}