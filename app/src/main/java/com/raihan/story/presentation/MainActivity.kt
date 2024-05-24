package com.raihan.story.presentation

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.raihan.story.base.BaseActivity
import com.raihan.story.databinding.ActivityMainBinding

class MainActivity() : BaseActivity<ActivityMainBinding>() {

    override fun assignBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity() {
        super.initActivity()
        installSplashScreen()
    }
}