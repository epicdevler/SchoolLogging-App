package com.cedarsstudio.internal.schoollogging.presentations.admins.screens.addStudents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cedarsstudio.internal.schoollogging.databinding.AdminAddStudentActivityLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAddStudent : AppCompatActivity() {

    private lateinit var binding: AdminAddStudentActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdminAddStudentActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}