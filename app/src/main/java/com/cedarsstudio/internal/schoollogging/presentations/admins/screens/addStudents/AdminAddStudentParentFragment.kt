package com.cedarsstudio.internal.schoollogging.presentations.admins.screens.addStudents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cedarsstudio.internal.schoollogging.databinding.AdminAddStudentParentFragmentLayoutBinding
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.StudentsVM
import com.cedarsstudio.internal.schoollogging.presentations.auth.screens.SignIn
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.toAdminScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminAddStudentParentFragment : Fragment() {

    private var _binding: AdminAddStudentParentFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var responseModal: ResponseModal
    private lateinit var vm: StudentsVM
    private val genders = mutableListOf(
        "Male", "Female"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(requireActivity())[StudentsVM::class.java].let { vm = it }
        _binding = AdminAddStudentParentFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
        binding.create.setOnClickListener {
            vm.create()
        }
    }


    private fun initUi() {
        responseModal = ResponseModal(requireActivity())
        binding.apply {
            firstNameInput.addTextChangedListener {
                vm._parentFirstName = it.toString()
            }
            lastNameInput.addTextChangedListener {
                vm._parentLastName = it.toString()
            }
            emailInput.addTextChangedListener {
                vm._parentEmail = it.toString()
            }
            phoneInput.addTextChangedListener {
                vm._parentPhone = it.toString()
            }
            genderInputLayout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    vm._parentGender = genders[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    private fun initObservers() {

        lifecycleScope.launch {
            vm.uiState.collect { uiState ->
                if (uiState.state == State.Loading) {
                    responseModal.showLoader( "${uiState.msg}", false)
                } else {
                    responseModal.dismiss()
                }
                when (uiState.state) {
                    State.Success -> {
                        responseModal.show(true, "${uiState.msg}", false) {
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        try {
            responseModal.dismiss()
        } catch (_: Exception) {
        }
    }

}