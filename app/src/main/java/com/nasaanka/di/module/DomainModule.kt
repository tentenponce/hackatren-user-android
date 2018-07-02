package com.nasaanka.di.module

import android.content.Context
import com.nasaanka.data.common.executor.JobExecutor
import com.nasaanka.data.impl.DeviceRepositoryImpl
import com.nasaanka.data.impl.TrainRepositoryImpl
import com.nasaanka.data.impl.UserRepositoryImpl
import com.nasaanka.data.service.FirebaseService
import com.nasaanka.di.AppContext
import com.nasaanka.domain.common.executor.PostExecutionThread
import com.nasaanka.domain.common.executor.ThreadExecutor
import com.nasaanka.domain.repository.DeviceRepository
import com.nasaanka.domain.repository.TrainRepository
import com.nasaanka.domain.repository.UserRepository
import com.nasaanka.util.provider.ThreadProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */

@Module
class DomainModule {

    /* Executor */
    @Provides
    @Singleton
    internal fun providesThreadExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @Provides
    @Singleton
    internal fun postExecutionThread(): PostExecutionThread {
        return ThreadProvider()
    }

    /* Repository */
    @Provides
    @Singleton
    internal fun userRepository(service: FirebaseService): UserRepository {
        return UserRepositoryImpl(service)
    }

    @Provides
    @Singleton
    internal fun deviceRepository(@AppContext context: Context): DeviceRepository {
        return DeviceRepositoryImpl(context)
    }

    @Provides
    @Singleton
    internal fun trainRepository(service: FirebaseService): TrainRepository {
        return TrainRepositoryImpl(service)
    }
}