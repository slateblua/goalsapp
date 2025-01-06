package com.bluesourceplus.bluedays.feature.create.module

import com.bluesourceplus.bluedays.feature.create.CreateGoalMode
import com.bluesourceplus.bluedays.feature.create.CreateViewModel
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCaseImpl
import com.bluesourceplus.bluedays.feature.create.usecases.UpdateGoalUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.UpdateGoalUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val createModule = module {
    viewModel { (mode: CreateGoalMode) -> CreateViewModel(mode) }

    single { AddGoalUseCaseImpl(get()) } bind AddGoalUseCase::class

    single { UpdateGoalUseCaseImpl(get()) } bind UpdateGoalUseCase::class
}