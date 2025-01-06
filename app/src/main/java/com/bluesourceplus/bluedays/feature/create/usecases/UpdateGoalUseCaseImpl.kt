package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo

class UpdateGoalUseCaseImpl(private val goalRepo: GoalRepo) : UpdateGoalUseCase {
    override suspend fun invoke(goal: GoalModel) {
        goalRepo.update(goal)
    }
}