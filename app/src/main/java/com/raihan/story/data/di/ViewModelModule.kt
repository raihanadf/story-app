package com.raihan.story.data.di

import com.raihan.story.presentation.MainViewModel
import com.raihan.story.presentation.fragment.auth.AuthViewModel
import com.raihan.story.presentation.fragment.login.LoginViewModel
import com.raihan.story.presentation.fragment.register.RegisterViewModel
import com.raihan.story.presentation.fragment.story.list.ListStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ListStoryViewModel(get(), get()) }
}
