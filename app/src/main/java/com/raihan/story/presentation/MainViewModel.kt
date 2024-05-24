package com.raihan.story.presentation

import androidx.lifecycle.ViewModel
import com.raihan.story.data.model.data.User
import com.raihan.story.data.repository.user.AuthRepository

class MainViewModel(
    private val repository: AuthRepository
) : ViewModel() {

}