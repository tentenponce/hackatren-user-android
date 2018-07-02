package com.nasaanka.di.module

import com.nasaanka.data.service.FirebaseService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideFirebaseService(): FirebaseService {
        return FirebaseService()
    }
}