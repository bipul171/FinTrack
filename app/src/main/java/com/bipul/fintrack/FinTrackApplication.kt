package com.bipul.fintrack

import android.app.Application
import com.bipul.fintrack.data.local.database.DatabaseInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FinTrackApplication : Application() {

    @Inject
    lateinit var databaseInitializer: DatabaseInitializer

    override fun onCreate() {
        super.onCreate()

        databaseInitializer.initialize()
    }
}