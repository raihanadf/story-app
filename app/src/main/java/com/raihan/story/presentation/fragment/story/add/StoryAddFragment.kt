package com.raihan.story.presentation.fragment.story.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentStoryAddBinding

class StoryAddFragment : BaseFragment<FragmentStoryAddBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryAddBinding {
        return FragmentStoryAddBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        with(binding) {
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}