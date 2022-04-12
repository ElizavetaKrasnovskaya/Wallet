package com.bsuir.cryptowallet.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CryptoWallet)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupNav()
    }

    fun setupNav() {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
//        navController = navHostFragment.navController
//        NavigationUI.setupActionBarWithNavController(this, navController)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.splashFragment, R.id.aboutAppFragment, R.id.loginFragment,
//                R.id.registrationFragment, R.id.creatingMnemonicFragment,
//                R.id.importingMnemonicFragment, R.id.walletInfoFragment -> {
//                    binding.bottomNavGraph.visibility = View.GONE
//                }
//                else -> {
//                    binding.bottomNavGraph.visibility = View.VISIBLE
//                }
//            }
//        }
        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_graph)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.chatFragment,
            R.id.settingsFragment,
            R.id.contactsFragment,))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.aboutAppFragment, R.id.loginFragment,
                R.id.registrationFragment, R.id.creatingMnemonicFragment,
                R.id.importingMnemonicFragment, R.id.walletInfoFragment -> {
                    binding.bottomNavGraph.visibility = View.GONE
                    binding.line.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavGraph.visibility = View.VISIBLE
                    binding.line.visibility = View.VISIBLE
                }
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}