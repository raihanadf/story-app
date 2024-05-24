package com.raihan.story.presentation.fragment.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.data.model.dto.auth.LoginRequest
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.databinding.FragmentLoginBinding
import com.raihan.story.utils.showToast
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    private val viewModel: LoginViewModel by inject()

    override fun doSomething() {
        super.doSomething()

        initListener()
        initObserver()
        playAnimation()
    }

    private fun initListener() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text?.trim().toString()
            val password = binding.edLoginPassword.text?.trim().toString()

            val request = LoginRequest(
                email,
                password,
            )

            if (email.isEmpty()) {
                binding.edLoginEmail.error =
                    getString(R.string.error_email_empty)
            } else if (password.isEmpty()) {
                binding.edLoginPassword.error =
                    getString(R.string.error_password_empty)
            } else {
                viewModel.login(request)
            }
        }

        binding.registerButton.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(direction)
        }
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingButton.root.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    showToast(requireActivity(), it.data.message)
                    val direction =
                        LoginFragmentDirections.actionLoginFragmentToListStoryFragment()
                    findNavController().navigate(direction)
                }

                is ApiStatus.Error -> {
                    showToast(requireActivity(), it.errorMessage)
                    binding.loadingButton.root.visibility = View.GONE
                }

                else -> {
                    binding.loadingButton.root.visibility = View.GONE
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(
            binding.imageView, View.TRANSLATION_X, -30f, 30f
        ).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f)
                .setDuration(100)
        val emailText =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f)
                .setDuration(100)
        val email =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f)
                .setDuration(100)
        val passwordText =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f)
                .setDuration(100)
        val password = ObjectAnimator.ofFloat(
            binding.passwordEditTextLayout,
            View.ALPHA,
            1f
        ).setDuration(100)
        val button = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f)
            .setDuration(100)
        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f)
                .setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailText,
                email,
                passwordText,
                password,
                button,
                register
            )
        }.start()
    }
}