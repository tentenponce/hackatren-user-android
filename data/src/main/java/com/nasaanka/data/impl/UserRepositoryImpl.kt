package com.nasaanka.data.impl

import com.nasaanka.data.service.FirebaseService
import com.nasaanka.domain.model.User
import com.nasaanka.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */
class UserRepositoryImpl @Inject constructor(val firebaseService: FirebaseService) : UserRepository {

    override fun saveUserLocation(user: User): Completable =
            firebaseService.write(FirebaseService.TRAIN_LOCATION_TABLE, user.id, user)

}