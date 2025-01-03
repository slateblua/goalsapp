package com.bluesourceplus.bluedays

import android.app.Application
import com.bluesourceplus.bluedays.data.database.module.dataModule
import com.bluesourceplus.bluedays.feature.create.module.createModule
import com.bluesourceplus.bluedays.feature.home.module.homeModule
import com.bluesourceplus.bluedays.feature.preferences.module.preferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BlueDaysApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin here, only once
        startKoin {
            androidContext(this@BlueDaysApp)
            androidLogger()
            modules(
                homeModule,
                dataModule,
                createModule,
                preferencesModule
            )
        }
    }
}