package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.taskfour.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }
    fun hideBottomNavigation() {
        binding.bottomNavigationView.visibility = View.GONE
    }


    override fun onSupportNavigateUp(): Boolean {
        //return navController.navigateUp() || super.onSupportNavigateUp()
        return navController.navigateUp()
    }


}
