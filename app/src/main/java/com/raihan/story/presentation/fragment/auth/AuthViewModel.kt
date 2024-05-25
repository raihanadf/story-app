package com.raihan.story.presentation.fragment.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raihan.story.utils.PreferenceManager


class AuthViewModel(val pref: PreferenceManager) : ViewModel() {

    val _isLogin = MutableLiveData<String>()
    val isLogin: LiveData<String> = _isLogin

    fun getToken() {
        _isLogin.value = pref.getToken
    }

    init {
        getToken()
    }
}