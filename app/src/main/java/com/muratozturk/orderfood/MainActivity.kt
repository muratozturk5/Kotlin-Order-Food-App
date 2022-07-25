package com.muratozturk.orderfood

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.muratozturk.orderfood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        NavigationUI.setupWithNavController(binding.navigationView, navHostFragment.navController)

        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu)
        val menu = popupMenu.menu
        binding.navigationView.setupWithNavController(menu, navHostFragment.navController)

//        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.paymentFragment) {
//                binding.navigationView.visibility = View.GONE
//            } else {
//                binding.navigationView.visibility = View.VISIBLE
//            }
//        }
    }

}