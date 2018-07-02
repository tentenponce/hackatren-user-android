package com.nasaanka.ui.main

import com.nasaanka.domain.interactor.train.GetTrainLocations
import com.nasaanka.domain.interactor.user.SaveUserLocation
import com.nasaanka.domain.model.User
import com.nasaanka.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/17/2018.
 */

class MainPresenter @Inject constructor(val saveUserLocation: SaveUserLocation,
                                        val getTrainLocations: GetTrainLocations) : BasePresenter<MainMvpView>() {

    private var mLatitude: Double = 0.0
        set(value) { // override set method
            field = value // set the value of this variable
            value.let { latitudeObservable.onNext(it) } // trigger on next
        }

    private var mLongitude: Double = 0.0
        set(value) {
            field = value
            value.let { longitudeObservable.onNext(it) }
        }

    val latitudeObservable = BehaviorSubject.createDefault(mLatitude) // observable to be triggered for latitude changes
    val longitudeObservable = BehaviorSubject.createDefault(mLongitude) // observable to be triggered for latitude changes

    init {
        Observable.zip(
                latitudeObservable,
                longitudeObservable,
                BiFunction { latitude: Double, longitude: Double ->
                    Pair(latitude, longitude)
                })
                .flatMapCompletable({
                    saveUserLocation.execute(User(id = "", latitude = it.first, longitude = it.second))
                })
                .doOnSubscribe { compositeDisposable::add }
                .subscribe()
    }

    fun setMyLocation(latitude: Double, longitude: Double) {
        mLatitude = latitude
        mLongitude = longitude

        if (isViewAttached) {
            mvpView?.redirectToMapLocation(mLongitude, mLatitude)
        }
    }

    fun getTrainLocations() {
        getTrainLocations.execute()
                .flatMap { Observable.fromIterable(it) }
                .doOnNext { mvpView?.updateTrainMarker(it) }
                .doOnSubscribe { compositeDisposable::add }
                .subscribe()
    }
}