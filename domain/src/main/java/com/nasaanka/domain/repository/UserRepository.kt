package com.nasaanka.domain.repository

import com.nasaanka.domain.model.User
import io.reactivex.Completable

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */
interface UserRepository {

    fun saveUserLocation(user: User): Completable
}