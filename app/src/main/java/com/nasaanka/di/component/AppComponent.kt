package com.nasaanka.di.component

import android.content.Context
import com.nasaanka.App
import com.nasaanka.di.AppContext
import com.nasaanka.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/17/2018.
 */

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    @AppContext
    fun context(): Context

    fun app(): App
}