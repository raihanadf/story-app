package com.raihan.story.presentation.fragment.settings

import androidx.lifecycle.ViewModel
import com.raihan.story.data.repository.user.AuthRepository

class SettingsViewModel(private val repository: AuthRepository) : ViewModel() {
    fun logout(): Boolean {
        return repository.logout()
    }
}