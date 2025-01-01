package com.bluesourceplus.bluedays.feature.preferences.module

import com.bluesourceplus.bluedays.feature.preferences.PreferencesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val preferencesModule = module {
    viewModel { PreferencesViewModel() }
}