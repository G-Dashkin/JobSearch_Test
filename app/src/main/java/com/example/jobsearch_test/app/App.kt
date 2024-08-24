package com.example.jobsearch_test.app

import android.app.Application
import com.example.jobsearch_test.di.DaggerAppComponent
import com.example.jobsearch_test.di.AppModule
import com.example.jobsearch_test.di.DaggerProvider
import com.example.jobsearch_test.entrance.di.EntranceFeatureDepsProvider
import com.example.jobsearch_test.home.di.HomeFeatureDepsProvider

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
        instance = this
    }

    private fun initDagger() {
        val appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        DaggerProvider.appComponent = appComponent
        EntranceFeatureDepsProvider.deps = appComponent
        HomeFeatureDepsProvider.deps = appComponent
    }
    companion object {
        lateinit var instance: App private set
    }
}