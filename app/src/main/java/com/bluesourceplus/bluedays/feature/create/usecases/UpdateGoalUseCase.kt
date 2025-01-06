package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.bluedays.data.GoalModel

interface UpdateGoalUseCase {
    suspend operator fun invoke(goal: GoalModel)
}