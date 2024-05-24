package com.raihan.story.presentation.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.story.data.di.viewModelModule
import com.raihan.story.data.model.dto.auth.RegisterRequest
import com.raihan.story.data.model.dto.auth.RegisterResponse
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.repository.user.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiStatus<RegisterResponse>>()
    val registerResult: LiveData<ApiStatus<RegisterResponse>> = _registerResult

    fun register(user: RegisterRequest) {
        viewModelScope.launch {
            repository.register(user).collect {
                _registerResult.value = it
            }
        }
    }
}