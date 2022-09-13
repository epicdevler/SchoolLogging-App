package com.cedarsstudio.internal.schoollogging.auth.screens.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.ForgotPasswordOtpFragmentBinding
import com.cedarsstudio.internal.schoollogging.databinding.ForgotPasswordRequestOtpFragmentBinding
import com.cedarsstudio.internal.schoollogging.databinding.SignInFragmentBinding
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed

class ForgotPasswordOTP : Fragment() {

    private var _binding: ForgotPasswordOtpFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ForgotPasswordOtpFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()

        handleUI()

        handleBackPressed()
        return binding.root
    }

    private fun handleUI() {
        binding.apply {
            authForgotSubmit.setOnClickListener {
                findNavController().navigate(ForgotPasswordOTPDirections.actionForgotPasswordOTPToForgotPasswordChange())
            }
            authForgotPasswordResendAction.setOnClickListener {

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