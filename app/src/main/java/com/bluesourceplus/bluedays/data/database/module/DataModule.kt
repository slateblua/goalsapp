package com.bluesourceplus.bluedays.data.database.module

import androidx.room.Room
import com.bluesourceplus.bluedays.data.GoalRepo
import com.bluesourceplus.bluedays.data.GoalRepoImpl
import com.bluesourceplus.bluedays.data.database.GoalDatabase
import com.bluesourceplus.bluedays.data.database.LocalDataSource
import com.bluesourceplus.bluedays.data.database.RoomLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

const val DATABASE_NAME = "goal-database"

val dataModule = module {
    // Provides GoalDao
    single { get<GoalDatabase>().getGoalDao() }

    // Provides GoalDatabase (Singleton)
    single {
        Room.databaseBuilder(
            androidContext(),
            GoalDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Binds RoomLocalDataSource to LocalDataSource
    single { RoomLocalDataSource(get()) } bind LocalDataSource::class

    // Binds GoalRepoImpl to GoalRepo
    single { GoalRepoImpl(get()) } bind GoalRepo::class
}