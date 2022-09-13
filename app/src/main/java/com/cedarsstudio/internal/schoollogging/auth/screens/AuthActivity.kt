package com.cedarsstudio.internal.schoollogging.auth.screens

import android.os.Bundle
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.databinding.AuthActivityBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: AuthActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthActivityBinding.inflate(layoutInflater).let { binding = it }

        setContentView(binding.root)

        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        val controller = hostFragment.navController

        controller.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.signIn && destination.id != R.id
            .forgotPasswordChangeSuccess) {
                binding.backBtn.visibility = View.VISIBLE
                binding.backBtn.setOnClickListener {
                    controller.navigateUp()
                }
            } else {
                binding.backBtn.visibility = View.INVISIBLE
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        Animatoo.animateSlideRight(this)
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        Animatoo.animateSlideRight(this)
        return super.getOnBackInvokedDispatcher()
    }
}