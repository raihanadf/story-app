package com.raihan.story.presentation.fragment.story.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentStoryListBinding
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
        initObserver()
    }

    private fun initObserver() {
        viewModel.username.observe(this) {
            binding.topBar.title = getString(R.string.greetings_message, it)
        }
    }

}