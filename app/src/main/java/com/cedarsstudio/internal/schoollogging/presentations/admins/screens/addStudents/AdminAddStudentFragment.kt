package com.cedarsstudio.internal.schoollogging.presentations.admins.screens.addStudents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cedarsstudio.internal.schoollogging.databinding.AdminAddStudentFragmentLayoutBinding
import com.cedarsstudio.internal.schoollogging.presentations.admins.vms.StudentsVM
import com.cedarsstudio.internal.schoollogging.presentations.modals.ResponseModal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAddStudentFragment : Fragment() {

    private var _binding: AdminAddStudentFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var responseModal: ResponseModal
    private lateinit var vm: StudentsVM

    private val genders = mutableListOf(
        "Male",
        "Female"
    )
    private val classes = mutableListOf(
        "JSS 1",
        "JSS 2",
        "JSS 3",
        "SS 1",
        "SS 2",
        "SS 3",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(requireActivity())[StudentsVM::class.java].let { vm = it }
        _binding = AdminAddStudentFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        binding.next.setOnClickListener {
            findNavController().navigate(
                AdminAddStudentFragmentDirections.actionFirstFragmentToSecondFragment()
            )
        }
    }

    private fun initUi() {
        responseModal = ResponseModal(requireActivity())
        binding.apply {
            firstNameInput.addTextChangedListener {
                vm._studentFirstName = it.toString()
            }
            lastNameInput.addTextChangedListener {
                vm._studentLastName = it.toString()
            }
            ageInput.addTextChangedListener {
                vm._studentAge = it.toString()
            }
            genderInputLayout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    vm._studentGender = genders[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
            classInputLayout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    vm._studentClass = classes[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        try{
            responseModal.dismiss()
        }catch (e: Exception){

        }
    }
}