package com.natasa.morsecodecompose

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MorseApp : Application() {
    override fun onCreate() {
        DynamicColors.applyToActivitiesIfAvailable(this)
        super.onCreate()
    }
}
