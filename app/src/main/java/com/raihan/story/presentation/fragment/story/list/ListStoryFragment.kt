package com.raihan.story.presentation.fragment.story.list

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentListStoryBinding
import org.koin.android.ext.android.inject

class ListStoryFragment : BaseFragment<FragmentListStoryBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentListStoryBinding {
        return FragmentListStoryBinding.inflate(inflater, container, false)
    }

    private val viewModel: ListStoryViewModel by inject()

    override fun doSomething() {
        super.doSomething()

        initObserver()
        initListener()
    }

    private fun initListener() {
        binding.logoutButton.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        viewModel.logout().apply {
            if (this) {
                val direction =
                    ListStoryFragmentDirections.actionListStoryFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
        }
    }

    private fun initObserver() {
        viewModel.username.observe(this) {
            binding.nameTextView.text = "Hi $it"
        }
    }

}