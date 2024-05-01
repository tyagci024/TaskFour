package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.taskfour.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController

        val bottomNW=binding.bottomNavigationView
        setupWithNavController(bottomNW, navController = navController)

       //setupActionBarWithNavController(navController)
        setSupportActionBar(null)
        bottomNW.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.loginFragment -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomNavigationView.visibility = View.GONE
                    true
                }
                R.id.firestoreCoinFragment -> {
                    navController.navigate(R.id.firestoreCoinFragment)
                    true
                }
                R.id.navigation_all -> {
                    navController.navigate(R.id.listFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    navController.navigate(R.id.favoritesPageFragment)
                    true
                }
                R.id.navigation_news -> {
                    navController.navigate(R.id.newsPageFragment)
                    true
                }
                else -> false
            }
        }
        hideNavBars()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        //return navController.navigateUp() || super.onSupportNavigateUp()
        return navController.navigateUp()
    }

    private fun hideNavBars() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}
