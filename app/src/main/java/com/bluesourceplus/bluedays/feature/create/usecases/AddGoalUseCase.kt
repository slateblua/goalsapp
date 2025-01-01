package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.bluedays.data.GoalModel

interface AddGoalUseCase {
    suspend operator fun invoke(goalModel: GoalModel)
}