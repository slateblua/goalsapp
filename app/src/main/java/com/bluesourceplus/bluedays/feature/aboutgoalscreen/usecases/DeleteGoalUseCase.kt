package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.bluedays.data.GoalModel

interface DeleteGoalUseCase {
    suspend operator fun invoke(goal: GoalModel)
}