package com.raihan.story.presentation.fragment.story.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raihan.story.data.repository.user.AuthRepository
import com.raihan.story.utils.PreferenceManager

class ListStoryViewModel(
    val repository: AuthRepository,
    val pref: PreferenceManager
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun getCurrentUserName() {
        _username.value = pref.name
    }

    fun logout(): Boolean {
        return repository.logout()
    }

    init {
        getCurrentUserName()
    }
}