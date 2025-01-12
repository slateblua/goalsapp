package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo
import com.bluesourceplus.bluedays.data.database.toGoalEnt

class DeleteGoalUseCaseImpl(private val goalRepo: GoalRepo) : DeleteGoalUseCase {
    override suspend fun invoke(goal: GoalModel) {
        goalRepo.delete(goal.toGoalEnt())
    }
}