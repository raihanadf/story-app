package com.raihan.story.presentation.fragment.story.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.raihan.story.base.BaseFragment
import com.raihan.story.data.model.dto.story.Story
import com.raihan.story.databinding.FragmentStoryDetailBinding

class StoryDetailFragment : BaseFragment<FragmentStoryDetailBinding>() {


    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryDetailBinding {
        return FragmentStoryDetailBinding.inflate(inflater, container, false)

    }

    var item: Story? = null

    override fun doSomething() {
        super.doSomething()
        item =
            StoryDetailFragmentArgs.fromBundle(requireArguments()).story

        with(binding) {
            imagePhoto.load(item?.photoUrl)
            authorTv.text = item?.name
            descriptionTv.text = item?.description
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}