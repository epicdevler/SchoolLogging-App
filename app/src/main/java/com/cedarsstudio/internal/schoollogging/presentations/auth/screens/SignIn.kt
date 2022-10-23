package com.cedarsstudio.internal.schoollogging.presentations.auth.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.databinding.SignInFragmentBinding
import com.cedarsstudio.internal.schoollogging.presentations.auth.vm.AuthVM
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.State.*
import com.cedarsstudio.internal.schoollogging.utils.handleBackPressed
import com.cedarsstudio.internal.schoollogging.utils.toAdminScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignIn : Fragment() {

    companion object {
        const val TAG = "SIGN_IN"
    }

    private var _binding: SignInFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val vm: AuthVM by viewModels()
    private lateinit var responseModal: ResponseModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        SignInFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()
        handleUI()

        initObservers()
        return binding.root
    }

    private fun initUi() {
        responseModal = ResponseModal(requireActivity())
        binding.apply {
            emailInput.addTextChangedListener {
                vm._email = it.toString()
            }
            passInput.addTextChangedListener {
                vm._password = it.toString()
            }
            authRememberMe.addOnCheckedStateChangedListener { checkBox, _ ->
                vm._rememberMe = checkBox.isChecked
            }
        }
    }

    private fun initObservers() {

        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                Log.e(TAG, "initObservers: $uiState")
                if (uiState.state == State.Loading) {
                    responseModal.showLoader( "${uiState.msg}", false)
                } else {
                    responseModal.dismiss()
                }
                when (uiState.state) {
                    Success -> {
                        responseModal.show(true, "${uiState.msg}", false) {
                            toAdminScreen(true)
                            responseModal.dismiss()
                        }
                    }
                    Error -> {
                        responseModal.show(msg = "${uiState.msg}", isCancellable = true)
                    }
                    else -> {}
                }
            }
        }

    }

    private fun handleUI() {
        binding.apply {
            authSignIn.setOnClickListener {
                vm.sigIn()
            }
            authSignUpAction.setOnClickListener {
                findNavController().navigate(SignInDirections.actionSignInToSignUp())
            }
            authForgotPassword.setOnClickListener {
                findNavController().navigate(SignInDirections.actionSignInToForgotPasswordRequest())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        try{
            responseModal.dismiss()
        }catch (e: Exception){

        }
    }


}