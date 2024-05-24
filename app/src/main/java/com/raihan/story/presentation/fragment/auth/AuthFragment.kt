package com.raihan.story.presentation.fragment.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentAuthBinding
import org.koin.android.ext.android.inject

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    private val viewModel: AuthViewModel by inject()

    override fun doSomething() {
        Log.d("OK", "doSomething: kontol")
        viewModel.isLogin.observe(viewLifecycleOwner) {
            when (it.isNotEmpty()) {

                true -> {
                    val direction =
                        AuthFragmentDirections.actionAuthFragmentToListStoryFragment()
                    findNavController().navigate(direction)
                }

                else -> {
                    val direction =
                        AuthFragmentDirections.actionAuthFragmentToLoginFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }

}