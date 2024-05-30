package com.raihan.story.presentation.fragment.story.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentStoryListBinding
import com.raihan.story.presentation.adapter.LoadingStateAdapter
import com.raihan.story.presentation.adapter.StoryAdapter
import org.koin.android.ext.android.inject

class StoryListFragment : BaseFragment<FragmentStoryListBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryListBinding {
        return FragmentStoryListBinding.inflate(inflater, container, false)
    }

    private val viewModel: StoryListViewModel by inject()

    override fun doSomething() {
        super.doSomething()

        initAdapterList()
        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.fabAddStory.setOnClickListener {
            val direction =
                StoryListFragmentDirections.actionListStoryFragmentToStoryAddFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initAdapterList() {
        val storyAdapter = StoryAdapter()
        val linearLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.storyRv.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
        binding.storyRv.layoutManager = linearLayoutManager
        viewModel.storyResult.observe(viewLifecycleOwner) { it ->
            storyAdapter.submitData(lifecycle, it)
        }
    }

    private fun initObserver() {
        with(viewModel) {
            username.observe(viewLifecycleOwner) {
                binding.topBar.title = getString(R.string.greetings_message, it)
            }
        }
    }

}