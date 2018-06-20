package com.nasaanka.di.component

import com.nasaanka.di.PerActivity
import com.nasaanka.di.module.ActivityModule
import com.nasaanka.ui.main.MainActivity
import dagger.Subcomponent

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/20/2018.
 */
@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
}