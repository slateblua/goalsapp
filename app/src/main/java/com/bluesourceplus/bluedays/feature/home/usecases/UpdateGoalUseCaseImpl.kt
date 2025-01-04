package com.bluesourceplus.bluedays.feature.home.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo

class UpdateGoalUseCaseImpl(private val goalRepo: GoalRepo) : UpdateGoalUseCase {
    override suspend fun invoke(goalModel: GoalModel) {
        goalRepo.update(goalModel)
    }
}