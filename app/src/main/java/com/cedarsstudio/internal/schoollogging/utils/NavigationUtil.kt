package com.cedarsstudio.internal.schoollogging.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.MainActivity
import com.cedarsstudio.internal.schoollogging.presentations.auth.screens.AuthActivity


fun Activity.toAuthScreen(isFinished: Boolean = false) {
    startActivity(
        Intent(this, AuthActivity::class.java)
    )
    if (isFinished) finish()
    Animatoo.animateSlideLeft(this)
}

fun Fragment.toAdminScreen(isFinished: Boolean = false) {
    startActivity(
        Intent(requireActivity(), MainActivity::class.java)
    )
    if (isFinished) requireActivity().finish()
    Animatoo.animateSlideLeft(requireActivity())
}

fun AppCompatActivity.handleBackPressed() {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Animatoo.animateSlideRight(this@handleBackPressed)
        }
    })
}

fun Fragment.handleBackPressed() {
    requireActivity().onBackPressedDispatcher.addCallback(this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
                Animatoo.animateSlideRight(requireContext())
            }
        })
}