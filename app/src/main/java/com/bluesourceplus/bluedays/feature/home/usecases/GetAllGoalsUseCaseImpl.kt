package com.bluesourceplus.bluedays.feature.home.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo
import kotlinx.coroutines.flow.Flow

class GetAllGoalsUseCaseImpl(private val goalRepo: GoalRepo) : GetAllGoalsUseCase {
    override fun invoke(): Flow<List<GoalModel>> {
        return goalRepo.getAll()
    }
}