package com.raihan.story.presentation

import android.util.Log
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raihan.story.R
import com.raihan.story.base.BaseActivity
import com.raihan.story.databinding.ActivityMainBinding
import com.raihan.story.utils.bottomBarScope

class MainActivity() : BaseActivity<ActivityMainBinding>() {

    override fun assignBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initActivity() {
        super.initActivity()
        installSplashScreen()
    }

    override fun doSomething() {
        super.doSomething()
        setupBottomBar()
    }

    private fun setupBottomBar() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController: NavController = navHost.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (bottomBarScope.contains(destination.id)) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }
}