package com.bluesourceplus.bluedays.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goalEnt")
    fun getAll(): Flow<List<GoalEnt>>

    @Query("SELECT * FROM goalEnt WHERE goalId = :id")
    fun getById(id: Int): Flow<GoalEnt>

    @Update
    fun update(goalEnt: GoalEnt)

    @Delete
    fun delete(goalEnt: GoalEnt)

    @Insert
    fun add(goalEnt: GoalEnt)
}