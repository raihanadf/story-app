package com.raihan.story.presentation.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.story.data.model.dto.auth.LoginRequest
import com.raihan.story.data.model.dto.auth.LoginResponse
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.repository.user.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ApiStatus<LoginResponse>>()
    val loginResult: LiveData<ApiStatus<LoginResponse>> = _loginResult

    fun login(user: LoginRequest) {
        viewModelScope.launch {
            repository.login(user).collect {
                _loginResult.value = it
            }
        }
    }
}