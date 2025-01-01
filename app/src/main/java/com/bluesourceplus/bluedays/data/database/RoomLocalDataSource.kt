package com.bluesourceplus.bluedays.data.database

import com.bluesourceplus.bluedays.data.GoalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource (private val dao: GoalDao): LocalDataSource {
    override fun getAll(): Flow<List<GoalModel>> {
        return dao.getAll().map { goalFlow ->
            goalFlow.map { goalEnt ->
                goalEnt.toGoalModel()
            }
        }
    }

    override fun getById(id: Int): Flow<GoalModel> {
        return dao.getById(id).map { goalEnt ->
            goalEnt.toGoalModel()
        }
    }

    override suspend fun update(goalModel: GoalModel) {
        dao.update(goalEnt = goalModel.toGoalEnt())
    }

    override suspend fun delete(goalEnt: GoalEnt) {
        dao.delete(goalEnt)
    }

    override suspend fun add(goalModel: GoalModel) {
        dao.add(goalEnt = goalModel.toGoalEnt())
    }
}