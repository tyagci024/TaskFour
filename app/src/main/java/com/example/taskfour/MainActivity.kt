package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskfour.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

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


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNW = binding.bottomNavigationView
        setupWithNavController(bottomNW, navController = navController)

        myWorkManager()
        setupActionBarWithNavController(navController)
        // setSupportActionBar(null)
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

    private fun myWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest =//ses gidiyor abi anlıyamıyorum
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP, myRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            AlertDialog.Builder(this)
                .setTitle("Çıkış Yap")
                .setMessage("Çıkış yapmak istiyor musunuz?")
                .setPositiveButton("Evet") { dialog, which ->
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(R.id.loginFragment)
                }
                .setNegativeButton("Hayır", null)
                .show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val currentDestinationId = navController.currentDestination?.id
        return if (currentDestinationId == R.id.listFragment && navController.graph.startDestinationId == R.id.loginFragment) {
            false
        } else {
            navController.navigateUp()
        }
    }

    private fun hideNavBars() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }
}
