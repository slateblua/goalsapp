package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import kotlinx.coroutines.flow.Flow

interface GetGoalByIdUseCase {
    operator fun invoke(id: Int): Flow<GoalModel>
}