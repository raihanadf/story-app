package com.raihan.story.presentation.fragment.story.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.raihan.story.data.model.dto.story.Story
import com.raihan.story.data.repository.story.StoryRepository
import com.raihan.story.presentation.adapter.StoryAdapter
import com.raihan.story.utils.DataDummy
import com.raihan.story.utils.MainDispatcherRule
import com.raihan.story.utils.PreferenceManager
import com.raihan.story.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.Test

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryListFragmentTest : KoinTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var repository: StoryRepository

    @Mock
    private lateinit var pref: PreferenceManager

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateStory()
        val data: PagingData<Story> = StoryTestPagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<Story>>()
        Mockito.`when`(repository.getAllStories(3)).thenReturn(expectedStory)
        expectedStory.value = data

        val storyViewModel = StoryListViewModel(repository, pref)
        val actualStory: PagingData<Story> =
            storyViewModel.storyResult.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data
        Mockito.`when`(repository.getAllStories(3)).thenReturn(expectedStory)
        val storyViewModel = StoryListViewModel(repository, pref)
        val actualStory: PagingData<Story> =
            storyViewModel.storyResult.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStory)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
