package com.nasaanka.di.component

import android.content.Context
import com.nasaanka.App
import com.nasaanka.di.AppContext
import com.nasaanka.di.module.AppModule
import com.nasaanka.di.module.DataModule
import com.nasaanka.di.module.DomainModule
import com.nasaanka.domain.common.executor.PostExecutionThread
import com.nasaanka.domain.common.executor.ThreadExecutor
import com.nasaanka.domain.repository.DeviceRepository
import com.nasaanka.domain.repository.UserRepository
import dagger.Component
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/17/2018.
 */

@Singleton
@Component(modules = [
    AppModule::class,
    DataModule::class,
    DomainModule::class])
interface AppComponent {

    @AppContext
    fun context(): Context

    fun app(): App

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun userRepository(): UserRepository

    fun deviceRepository(): DeviceRepository
}