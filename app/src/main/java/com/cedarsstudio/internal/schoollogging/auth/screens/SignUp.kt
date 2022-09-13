package com.cedarsstudio.internal.schoollogging.auth.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.cedarsstudio.internal.schoollogging.databinding.SignUpFragmentBinding

class SignUp : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        SignUpFragmentBinding.inflate(inflater, container, false).let { _binding = it }

        initUi()

        handleUI()

        return binding.root
    }

    private fun handleUI() {
        binding.apply {
            authSignInAction.setOnClickListener {
                findNavController().navigateUp()
                Animatoo.animateSlideRight(requireContext())
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