package com.bluesourceplus.bluedays.feature.home.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import kotlinx.coroutines.flow.Flow

interface GetAllGoalsUseCase {
    operator fun invoke(): Flow<List<GoalModel>>
}