package com.nasaanka.data.impl

import com.nasaanka.data.service.FirebaseService
import com.nasaanka.domain.model.Train
import com.nasaanka.domain.repository.TrainRepository
import io.reactivex.Observable
import javax.inject.Inject

class TrainRepositoryImpl @Inject constructor(val firebaseService: FirebaseService) : TrainRepository {

    override fun getTrainLocations(): Observable<List<Train>> {
        return firebaseService.readByTable(FirebaseService.TRAIN_LOCATION_TABLE)
                .map {
                    var trains: List<Train> = ArrayList()

                    it.children.forEach {
                        it.getValue(Train::class.java)?.let { trains += it }
                    }

                    trains
                }
    }
}