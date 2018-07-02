package com.nasaanka.domain.interactor.train

import com.nasaanka.domain.common.base.ObservableUseCaseNoParam
import com.nasaanka.domain.common.executor.PostExecutionThread
import com.nasaanka.domain.common.executor.ThreadExecutor
import com.nasaanka.domain.model.Train
import com.nasaanka.domain.repository.TrainRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTrainLocations @Inject constructor(threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread,
                                            private val trainRepository: TrainRepository) :
        ObservableUseCaseNoParam<List<Train>>(threadExecutor, postExecutionThread) {

    override fun buildObservable(): Observable<List<Train>> {
        return trainRepository.getTrainLocations()
    }
}