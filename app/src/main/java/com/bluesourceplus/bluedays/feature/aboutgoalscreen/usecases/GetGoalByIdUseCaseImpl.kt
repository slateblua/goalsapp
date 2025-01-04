package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.data.GoalRepo
import kotlinx.coroutines.flow.Flow

class GetGoalByIdUseCaseImpl(private val goalRepo: GoalRepo) : GetGoalByIdUseCase {
    override fun invoke(id: Int): Flow<GoalModel> {
        return goalRepo.getById(id)
    }
}