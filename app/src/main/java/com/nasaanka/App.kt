package com.nasaanka

import android.app.Application
import android.content.Context
import com.nasaanka.di.component.AppComponent
import com.nasaanka.di.module.AppModule
import com.nasaanka.di.component.DaggerAppComponent

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/17/2018.
 */
class App : Application() {

    internal var mApplicationComponent: AppComponent? = null

    companion object {
        fun get(context: Context) = context.applicationContext as App
    }

    fun getComponent(): AppComponent? {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
        }

        return mApplicationComponent
    }
}