package com.bluesourceplus.bluedays.data.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.LocalDate

@Database(entities = [GoalEnt::class], version = 1)
@TypeConverters(LocalDateConverters::class)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun getGoalDao(): GoalDao
}

@Entity
data class GoalEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
)

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