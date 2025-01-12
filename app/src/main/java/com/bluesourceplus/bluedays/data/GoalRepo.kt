package com.bluesourceplus.bluedays.data

import com.bluesourceplus.bluedays.data.database.GoalEnt
import kotlinx.coroutines.flow.Flow

interface GoalRepo {
    fun getAll(): Flow<List<GoalModel>>

    fun getById(goalId: Int): Flow<GoalModel>

    suspend fun update(goalModel: GoalModel)

    suspend fun delete(goalEnt: GoalEnt)

    suspend fun add(goalModel: GoalModel)
}