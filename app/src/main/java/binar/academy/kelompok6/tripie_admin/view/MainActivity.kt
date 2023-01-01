@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)

package binar.academy.kelompok6.tripie_admin.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.databinding.ActivityMainBinding
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("OPT_IN_USAGE", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused"
)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        setDrawer()

    }

    private fun setDrawer(){
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.dashboardFragment,
            R.id.flightFragment,
            R.id.bookedTicketFragment,
            R.id.logout
        ), binding.drawerAdmin)

        setupActionBarWithNavController(navController, binding.drawerAdmin)
        binding.navigationView.setupWithNavController(navController)

        setHeaderText()

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout -> {
                    removeToken()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(it, navController)

                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.drawerAdmin.closeDrawers()
                    }, 1000)
                }
            }
            true
        }
    }

    private fun setHeaderText() {
        val header = binding.navigationView.getHeaderView(0)
        val tvHeaderEmail = header.findViewById(R.id.tvHeaderEmail) as TextView
        val tvHeaderName = header.findViewById(R.id.tvHeaderName) as TextView

        sharedPref.getEmail.asLiveData().observe(this) { email ->
            tvHeaderEmail.text = email
        }

        sharedPref.getName.asLiveData().observe(this){ name ->
            tvHeaderName.text = name
        }
    }

    private fun removeToken() {
        GlobalScope.launch {
            sharedPref.removeToken()
        }
        Toast.makeText(this@MainActivity, "Logout Sukses!", Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}