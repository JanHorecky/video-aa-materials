package com.raywenderlich.android.menagerie.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.menagerie.R
import com.raywenderlich.android.menagerie.databinding.ActivityLoginBinding
import com.raywenderlich.android.menagerie.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), LoginView {

  private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
  private val loginViewModel by viewModels<LoginViewModel>()

  companion object {
    fun getIntent(context: Context) = Intent(context, LoginActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    loginViewModel.setView(this)
    loginViewModel.start()

    setContentView(binding.root)
    setupUi()
  }

  private fun setupUi() {
    binding.loginButton.onClick {
      loginViewModel.logIn()

      binding.loginButton.visibility = View.GONE
      binding.lottieLoading.visibility = View.VISIBLE

//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//        binding.loginButton.transformToProgress()
//      }
    }
  }

  override fun onLoggedIn() { // todo button animation, transition, progress
    // binding.loginButton.reverseAnimation { showPets() }
    showPets()
  }

  override fun showPets() {
    startActivity(MainActivity.getIntent(this))
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
  }
}