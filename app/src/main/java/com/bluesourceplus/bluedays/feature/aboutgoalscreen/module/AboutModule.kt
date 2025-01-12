package com.bluesourceplus.bluedays.feature.aboutgoalscreen.module

import com.bluesourceplus.bluedays.feature.aboutgoalscreen.AboutGoalViewModel
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.DeleteGoalUseCase
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.DeleteGoalUseCaseImpl
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.GetGoalByIdUseCase
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.GetGoalByIdUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val aboutModule = module {
    viewModel { AboutGoalViewModel() }

    single { GetGoalByIdUseCaseImpl(get()) } bind GetGoalByIdUseCase::class

    single { DeleteGoalUseCaseImpl(get()) } bind DeleteGoalUseCase::class
}