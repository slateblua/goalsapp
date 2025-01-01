package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo
import com.bluesourceplus.bluedays.data.GoalRepoImpl

class AddGoalUseCaseImpl (private val goalRepo: GoalRepo) : AddGoalUseCase {
    override suspend operator fun invoke(goalModel: GoalModel) {
        goalRepo.add(goalModel)
    }
}