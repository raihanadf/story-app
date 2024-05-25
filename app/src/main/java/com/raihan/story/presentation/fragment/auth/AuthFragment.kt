package com.raihan.story.presentation.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentAuthBinding
import com.raihan.story.utils.PreferenceManager
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AuthFragment : BaseFragment<FragmentAuthBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    private val preferenceManager: PreferenceManager by inject()

    override fun doSomething() {
        lifecycleScope.launch {
            when (preferenceManager.getToken.isNotEmpty()) {

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