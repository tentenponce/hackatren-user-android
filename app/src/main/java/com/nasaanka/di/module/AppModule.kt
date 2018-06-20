package com.nasaanka.di.module

import android.content.Context
import com.nasaanka.App
import com.nasaanka.di.AppContext
import dagger.Module
import dagger.Provides

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/19/2018.
 */
@Module
class AppModule(val app: App) {

    @Provides
    fun provideApp(): App = app

    @Provides
    @AppContext
    fun provideContext(): Context = app
}