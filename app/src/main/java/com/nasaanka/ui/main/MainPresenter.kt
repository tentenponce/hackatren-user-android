package com.nasaanka.ui.main

import com.nasaanka.ui.base.BasePresenter
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/17/2018.
 */

class MainPresenter @Inject constructor() : BasePresenter<MainMvpView>() {

    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    fun setMyLocation(latitude: Double, longitude: Double) {
        mLatitude = latitude
        mLongitude = longitude

        if (isViewAttached) {
            mvpView?.redirectToMapLocation(mLongitude, mLatitude)
        }
    }
}