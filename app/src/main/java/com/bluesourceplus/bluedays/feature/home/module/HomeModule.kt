package com.bluesourceplus.bluedays.feature.home.module

import com.bluesourceplus.bluedays.feature.home.HomeViewModel
import com.bluesourceplus.bluedays.feature.home.usecases.GetAllGoalsUseCase
import com.bluesourceplus.bluedays.feature.home.usecases.GetAllGoalsUseCaseImpl
import com.bluesourceplus.bluedays.feature.home.usecases.UpdateGoalUseCase
import com.bluesourceplus.bluedays.feature.home.usecases.UpdateGoalUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }

    single { GetAllGoalsUseCaseImpl(get()) } bind GetAllGoalsUseCase::class

    single { UpdateGoalUseCaseImpl(get()) } bind UpdateGoalUseCase::class
}