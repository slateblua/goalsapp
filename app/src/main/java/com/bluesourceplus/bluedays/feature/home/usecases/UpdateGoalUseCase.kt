package com.bluesourceplus.bluedays.feature.home.usecases

import com.bluesourceplus.bluedays.data.GoalModel

interface UpdateGoalUseCase {
    suspend operator fun invoke(goalModel: GoalModel)
}