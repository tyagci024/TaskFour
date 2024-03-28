package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taskfour.databinding.ActivityMainBinding

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

        // BottomNavigationView ayarlanır
        val bottomNW=binding.bottomNavigationView
        setupWithNavController(bottomNW, navController = navController)

        // ActionBarın ayarlanması
        setupActionBarWithNavController(navController)
        bottomNW.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_all -> {
                    // ListFragment'a git
                    navController.navigate(R.id.listFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    // Favoriler Fragment'a git
                    navController.navigate(R.id.favoritesPageFragment)
                    true
                }
                R.id.navigation_news -> {
                    // Haberler Fragment'a git
                    navController.navigate(R.id.newsPageFragment)
                    true
                }
                else -> false
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
