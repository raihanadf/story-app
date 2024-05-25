package com.raihan.story.presentation.fragment.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.databinding.FragmentSettingsBinding
import com.raihan.story.presentation.fragment.story.list.StoryListFragmentDirections
import com.raihan.story.utils.ext.showChooserDialog
import org.koin.android.ext.android.inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater, container, false)
    }

    private val viewModel: SettingsViewModel by inject()

    override fun doSomething() {
        super.doSomething()

        initListener()
    }

    private fun initListener() {
        binding.logoutButton.setOnClickListener {
            showChooserDialog(
                title = getString(R.string.logout),
                message = getString(R.string.logout_message),
                positiveButtonText = getString(R.string.yes),
                negativeButtonText = getString(R.string.no),
                onPositiveClick = {
                    logOut()
                }
            )
        }

        binding.changeLanguageButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    private fun logOut() {
        viewModel.logout().apply {
            if (this) {
                val direction =
                    SettingsFragmentDirections.actionSettingsFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
        }
    }
}