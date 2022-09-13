package com.cedarsstudio.internal.schoollogging.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.auth.screens.AuthActivity


fun Activity.toAuthScreen() {
    startActivity(
        Intent(this, AuthActivity::class.java)
    )
    Animatoo.animateSlideLeft(this)
}

fun AppCompatActivity.handleBackPressed() {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Animatoo.animateSlideRight(this@handleBackPressed)
        }
    })
}

fun Fragment.handleBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
            Animatoo.animateSlideRight(requireContext())
        }
    })
}