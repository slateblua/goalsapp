package com.bluesourceplus.bluedays.data

import com.bluesourceplus.bluedays.data.database.GoalEnt
import com.bluesourceplus.bluedays.data.database.LocalDataSource
import kotlinx.coroutines.flow.Flow

class GoalRepoImpl (private val localDataSource: LocalDataSource) : GoalRepo {
    override fun getAll(): Flow<List<GoalModel>> {
        return localDataSource.getAll()
    }

    override fun getById(id: Int): Flow<GoalModel> {
        return localDataSource.getById(id)
    }

    override suspend fun update(goalModel: GoalModel) {
        localDataSource.update(goalModel)
    }

    override suspend fun delete(goalEnt: GoalEnt) {
        localDataSource.delete(goalEnt)
    }

    override suspend fun add(goalModel: GoalModel) {
        localDataSource.add(goalModel)
    }
}