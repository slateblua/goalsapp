package com.bluesourceplus.bluedays.data.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Database(entities = [GoalEnt::class], version = 1)
abstract class GoalDatabase : RoomDatabase() {
    abstract fun getGoalDao(): GoalDao
}

@Entity
data class GoalEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
)