package com.bluesourceplus.bluedays.data.database

import com.bluesourceplus.bluedays.data.GoalModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getAll(): Flow<List<GoalModel>>

    fun getById(id: Int): Flow<GoalModel>

    suspend fun update(goalModel: GoalModel)

    suspend fun delete(goalEnt: GoalEnt)

    suspend fun add(goalModel: GoalModel)
}