package binar.academy.kelompok6.tripie_admin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.databinding.ActivitySplashScreenBinding
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashTime : Long = 5000

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTime)
    }
}