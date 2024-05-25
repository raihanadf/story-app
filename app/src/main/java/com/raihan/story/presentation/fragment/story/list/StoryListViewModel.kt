package com.raihan.story.presentation.fragment.story.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raihan.story.data.repository.story.StoryRepository
import com.raihan.story.data.repository.user.AuthRepository
import com.raihan.story.utils.PreferenceManager

class StoryListViewModel(
    val repository: StoryRepository,
    val pref: PreferenceManager
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun getCurrentUserName() {
        _username.value = pref.name
    }

    init {
        getCurrentUserName()
    }
}