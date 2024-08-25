package com.example.jobsearch_test.di

import android.app.Application
import android.content.Context
import com.example.jobsearch_test.app.App
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.data.database.dao.FavoritesDao
import com.example.jobsearch_test.data.database.factory.AppDatabase
import com.example.jobsearch_test.data.repository.VacanciesRepositoryImpl
import com.example.jobsearch_test.data.storage.VacanciesStorageImpl
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.data_api.storage.VacanciesStorage
import com.example.jobsearch_test.presentation.navigation.NavigatorLifecycle
import com.example.jobsearch_test.presentation.navigation.RouterImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    // Main provides--------------------------------------------------------------------------------
    @Singleton
    @Provides
    fun provideRouterImpl(): RouterImpl {
        return RouterImpl()
    }

    @Provides
    fun provideRouter(routerImpl: RouterImpl): Router {
        return routerImpl
    }

    @Provides
    fun provideNavigatorLifecycle(routerImpl: RouterImpl): NavigatorLifecycle {
        return routerImpl
    }

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase = AppDatabase.createDatabase(context = App.instance)

    @Provides
    @Singleton
    fun provideFavoritesDao(db: AppDatabase): FavoritesDao = db.favoritesDao()

    @Singleton
    @Provides
    fun provideVacanciesStorage(
        favoritesDao: FavoritesDao
    ): VacanciesStorage = VacanciesStorageImpl(favoritesDao = favoritesDao)

    @Singleton
    @Provides
    fun provideVacanciesRepository(
        context: Context,
        vacanciesStorage: VacanciesStorage,
    ): VacanciesRepository = VacanciesRepositoryImpl(
        context = context,
        vacanciesStorage = vacanciesStorage
    )
}