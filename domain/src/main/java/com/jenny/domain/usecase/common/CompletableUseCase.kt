package com.jenny.domain.usecase.common

import com.jenny.common.ApplicationExceptions
import com.jenny.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<in P>(private val postExecutionThread: PostExecutionThread?) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @NonNull
    abstract fun buildUseCaseObservable(params: P? = null): Completable

    fun execute(observer: DisposableCompletableObserver?, params: P? = null) {
        if (observer == null) {
            throw IllegalArgumentException(ApplicationExceptions.NO_NULL_OBSERVER)
        }
        if (postExecutionThread == null) {
            throw IllegalArgumentException(ApplicationExceptions.NO_NULL_EXECUTION_THREAD)
        }
        if (postExecutionThread.scheduler == null) {
            throw IllegalArgumentException(ApplicationExceptions.NO_NULL_SCHEDULER)
        }
        val completable =
            this.buildUseCaseObservable(params).subscribeOn(Schedulers.io()).observeOn(postExecutionThread.scheduler)
        addDisposable(completable.subscribeWith(observer))
    }

    fun disposeAll() {
        this.compositeDisposable.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        this.compositeDisposable.add(disposable)
    }
}
