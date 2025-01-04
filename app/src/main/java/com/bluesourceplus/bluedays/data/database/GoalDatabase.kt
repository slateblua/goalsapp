package com.bluesourceplus.bluedays.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.LocalDate

@Database(entities = [GoalEnt::class], version = 1)
@TypeConverters(LocalDateConverters::class)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun getGoalDao(): GoalDao
}

class LocalDateConverters {
    @TypeConverter
    fun fromString(value: String): LocalDate {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun toString(localDate: LocalDate): String {
        return localDate.toString()
    }
}