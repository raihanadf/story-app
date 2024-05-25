package com.raihan.story.presentation.fragment.story.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.databinding.FragmentStoryListBinding
import com.raihan.story.presentation.adapter.StoryAdapter
import com.raihan.story.utils.ext.showDialog
import com.raihan.story.utils.showToast
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
        binding.storyRv.adapter = storyAdapter
        binding.storyRv.layoutManager = linearLayoutManager
        viewModel.storyResult.observe(viewLifecycleOwner) { it ->
            when (it) {
                is ApiStatus.Loading -> {
                    binding.errorFetching.root.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    binding.progressIndicator.visibility = View.GONE
                    storyAdapter.submitList(it.data.listStory)
                }

                else -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.errorFetching.root.visibility = View.VISIBLE
                    showToast(
                        requireActivity(),
                        getString(R.string.error_fetching)
                    )
                }
            }
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