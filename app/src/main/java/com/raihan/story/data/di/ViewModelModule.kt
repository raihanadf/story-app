package com.raihan.story.data.di

import com.raihan.story.presentation.fragment.auth.AuthViewModel
import com.raihan.story.presentation.fragment.login.LoginViewModel
import com.raihan.story.presentation.fragment.register.RegisterViewModel
import com.raihan.story.presentation.fragment.settings.SettingsViewModel
import com.raihan.story.presentation.fragment.story.add.StoryAddViewModel
import com.raihan.story.presentation.fragment.story.list.StoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { StoryListViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { StoryAddViewModel(get()) }
}
