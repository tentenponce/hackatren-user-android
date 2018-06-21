package com.nasaanka.ui.main

import com.nasaanka.ui.base.MvpView

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/22/2018.
 */
interface MainMvpView : MvpView {

    fun redirectToMapLocation(longitude: Double, latitude: Double)
}