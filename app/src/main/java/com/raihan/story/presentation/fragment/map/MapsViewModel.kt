package com.raihan.story.presentation.fragment.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.model.dto.story.StoryAllResponse
import com.raihan.story.data.repository.story.StoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {
    private var _storyResult = MutableLiveData<ApiStatus<StoryAllResponse>>()
    val storyResult: LiveData<ApiStatus<StoryAllResponse>> = _storyResult

    fun getAllStoriesWithLocation() {
        viewModelScope.launch {
            repository.getAllStoriesWithLocation().collect {
                _storyResult.value = it
            }
        }
    }

}