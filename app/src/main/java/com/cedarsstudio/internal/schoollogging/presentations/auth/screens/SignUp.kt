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
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.SignUpFragmentBinding
import com.cedarsstudio.internal.schoollogging.presentations.auth.vm.AuthVM
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.toAdminScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUp : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val vm: AuthVM by viewModels()

    private lateinit var responseModal: ResponseModal
    private lateinit var loaderModal: ResponseModal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        SignUpFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()

        handleUI()

        initObservers()

        return binding.root
    }


    private fun initObservers() {

        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                Log.e(SignIn.TAG, "initObservers: $uiState")
                if (uiState.state == State.Loading) {
                    responseModal.showLoader( "${uiState.msg}", false)
                } else {
                    responseModal.dismiss()
                }
                when (uiState.state) {
                    State.Success -> {
                        responseModal.show(true, "${uiState.msg}", false) {
                            toAdminScreen(true)
                            responseModal.dismiss()
                        }
                    }
                    State.Error -> {
                        responseModal.show(msg = "${uiState.msg}", isCancellable = true)
                    }
                    else -> {}
                }
            }
        }

    }

    private fun handleUI() {
        binding.apply {
            authSignInAction.setOnClickListener { findNavController().navigateUp() }
            authSignUp.setOnClickListener{ vm.sigUp() }
        }
    }

    private fun initUi() {
        responseModal = ResponseModal(requireActivity())
        loaderModal = ResponseModal(requireActivity())
        binding.apply {
            authNameInput.addTextChangedListener {
                vm._name = it.toString()
            }
            emailInput.addTextChangedListener {
                vm._email = it.toString()
            }
            authPassInput.addTextChangedListener {
                vm._password = it.toString()
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        responseModal.dismiss()
    }


}