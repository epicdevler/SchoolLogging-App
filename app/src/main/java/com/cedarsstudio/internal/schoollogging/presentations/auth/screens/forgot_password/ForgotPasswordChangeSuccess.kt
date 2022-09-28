package com.cedarsstudio.internal.schoollogging.presentations.auth.screens.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.*
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed

class ForgotPasswordChangeSuccess : Fragment() {

    private var _binding: ForgotPasswordSuccessFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ForgotPasswordSuccessFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()

        handleUI()

        handleBackPressed()
        return binding.root
    }

    private fun handleUI() {
        binding.apply {
            authForgotSubmit.setOnClickListener {
//                findNavController().navigate(SignInDirections.actionSignInToSignUp())
            }

    }
    }

    private fun initUi() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}