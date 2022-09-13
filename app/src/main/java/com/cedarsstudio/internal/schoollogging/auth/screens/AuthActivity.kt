package com.cedarsstudio.internal.schoollogging.auth.screens

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.AuthActivityBinding
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: AuthActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthActivityBinding.inflate(layoutInflater).let { binding = it }

        setContentView(binding.root)

//        val hostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
//        val controller = hostFragment.navController


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