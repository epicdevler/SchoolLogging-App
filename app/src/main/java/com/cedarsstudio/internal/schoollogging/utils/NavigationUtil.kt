package com.cedarsstudio.internal.schoollogging.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.MainActivity
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.RosterActivity
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.addStudents.AdminAddStudent
import com.cedarsstudio.internal.schoollogging.presentations.auth.screens.AuthActivity


fun Activity.toAuthScreen(isFinished: Boolean = false)  =
    navigateTo(AuthActivity::class.java, isFinished)

fun Activity.toAdminAddStudentScreen(isFinished: Boolean = false) =
    navigateTo(AdminAddStudent::class.java, isFinished)

fun Fragment.toAdminScreen(isFinished: Boolean = false) {
    requireActivity().navigateTo(MainActivity::class.java, isFinished)
}

fun Activity.navigateTo(activity: Class<*>, isFinished: Boolean = false) {
    startActivity(
        Intent(this, activity)
    )
    if (isFinished) finish()
    Animatoo.animateSlideLeft(this)
}

fun Activity.toAdminScreen(isFinished: Boolean = false) {
    navigateTo(MainActivity::class.java, isFinished)
}
fun Activity.toRoster(isFinished: Boolean = false) {
    navigateTo(RosterActivity::class.java, isFinished)
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