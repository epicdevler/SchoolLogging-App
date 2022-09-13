package com.cedarsstudio.internal.schoollogging.auth.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.SignInFragmentBinding
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed

class SignIn : Fragment() {

    private var _binding: SignInFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SignInFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()

        handleUI()

        handleBackPressed()
        return binding.root
    }

    private fun handleUI() {
        binding.apply {
            authSignUpAction.setOnClickListener {
                findNavController().navigate(SignInDirections.actionSignInToSignUp())
                Animatoo.animateSlideLeft(requireContext())
            }
            authForgotPassword.setOnClickListener {
                findNavController().navigate(SignInDirections.actionSignInToForgotPasswordRequest())
                Animatoo.animateSlideLeft(requireContext())
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