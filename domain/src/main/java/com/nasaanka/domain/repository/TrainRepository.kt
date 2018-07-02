package com.nasaanka.domain.repository

import com.nasaanka.domain.model.Train
import io.reactivex.Observable

interface TrainRepository {

    fun getTrainLocations(): Observable<List<Train>>
}