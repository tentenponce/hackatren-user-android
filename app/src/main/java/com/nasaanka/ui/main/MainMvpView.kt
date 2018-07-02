package com.nasaanka.ui.main

import com.nasaanka.domain.model.Train
import com.nasaanka.ui.base.MvpView

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/22/2018.
 */
interface MainMvpView : MvpView {

    fun updateTrainMarker(train: Train)

    fun redirectToMapLocation(longitude: Double, latitude: Double)
}