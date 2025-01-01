package com.bluesourceplus.bluedays.feature.create.module

import com.bluesourceplus.bluedays.feature.create.CreateViewModel
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val createModule = module {
    viewModel { CreateViewModel() }

    single { AddGoalUseCaseImpl(get()) } bind AddGoalUseCase::class
}