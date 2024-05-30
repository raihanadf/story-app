package com.raihan.story.presentation.fragment.story.add

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.data.model.dto.story.StoryAddResponse
import com.raihan.story.data.repository.story.StoryRepository
import kotlinx.coroutines.launch

class StoryAddViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uploadStoryResult =
        MutableLiveData<ApiStatus<StoryAddResponse>>()
    val uploadStoryResult: LiveData<ApiStatus<StoryAddResponse>> =
        _uploadStoryResult

    fun uploadStory(
        imageUri: Uri,
        description: String,
        lat: Double,
        long: Double
    ) {
        viewModelScope.launch {
            repository.uploadStory(imageUri, description, lat, long).collect {
                _uploadStoryResult.value = it
            }
        }
    }
}