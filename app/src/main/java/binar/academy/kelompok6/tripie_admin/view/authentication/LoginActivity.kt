package binar.academy.kelompok6.tripie_admin.view.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.ActivityLoginBinding
import binar.academy.kelompok6.tripie_admin.model.request.LoginRequest
import binar.academy.kelompok6.tripie_admin.model.response.LoginResponse
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val authViewModel : AuthViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        authViewModel.login(LoginRequest(
            email = binding.etEmailAdmin.text.toString(),
            password = binding.etPassAdmin.text.toString()
        ))
        authViewModel.loginObserver().observe(this){
            when(it){
                is ApiResponse.Loading -> {
                    showLoading()
                    Log.d("Loading: ", it.toString())
                }
                is ApiResponse.Success -> {
                    stopLoading()
                    if (it.data!!.role != "Admin"){
                        Toast.makeText(this, "Anda bukan admin!!", Toast.LENGTH_SHORT).show()
                    }else{
                        processLogin(it.data)
                    }
                    Log.d("Success: ", it.toString())
                }
                is ApiResponse.Error -> {
                    stopLoading()
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", it.toString())
                }
            }
        }

    }

    private fun processLogin(data : LoginResponse) {
        saveData(data.token, data.email, data.name)
        Toast.makeText(this, "Login Sukses!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun saveData(token : String, email : String, name : String) {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.saveData(token, email, name)
        }
    }

    private fun stopLoading() {
        binding.progressBarLogin.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBarLogin.visibility = View.VISIBLE
    }
}