package com.cedarsstudio.internal.schoollogging.presentations.admins.vms

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cedarsstudio.internal.schoollogging.remote.students.StudentLists
import com.cedarsstudio.internal.schoollogging.remote.students.models.Parent
import com.cedarsstudio.internal.schoollogging.remote.students.models.Student
import com.cedarsstudio.internal.schoollogging.remote.students.models.StudentRoster
import com.cedarsstudio.internal.schoollogging.remote.utils.Response
import com.cedarsstudio.internal.schoollogging.utils.State
import com.cedarsstudio.internal.schoollogging.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("PropertyName")
@HiltViewModel
class StudentsVM @Inject constructor(
    private val studentLists: StudentLists, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val TAG = "STUDENTS_VM"
    }

    private val student: Student = Student()
    private val parent: Parent = Parent()
    var _studentFirstName: String
        set(value) {
            student.apply { firstName = value }
        }
        get() = student.firstName ?: ""

    var _studentLastName: String
        set(value) {
            student.apply { lastName = value }
        }
        get() = student.lastName ?: ""

    var _studentAge: String
        set(value) {
            student.apply { age = value }
        }
        get() = student.age ?: ""

    var _studentGender: String
        set(value) {
            student.apply { gender = value }
        }
        get() = student.gender ?: ""

    var _studentClass: String
        set(value) {
            student.apply { classSession = value }
        }
        get() = student.classSession ?: ""

    var _parentFirstName: String
        set(value) {
            parent.apply { firstName = value }
        }
        get() = parent.firstName ?: ""
    var _parentLastName: String
        set(value) {
            parent.apply { lastName = value }
        }
        get() = parent.lastName ?: ""
    var _parentGender: String
        set(value) {
            parent.apply { gender = value }
        }
        get() = parent.gender ?: ""
    var _parentEmail: String
        set(value) {
            parent.apply { email = value }
        }
        get() = parent.email ?: ""
    var _parentPhone: String
        set(value) {
            parent.apply { phone = value }
        }
        get() = parent.phone ?: ""

    private val _uiState: MutableStateFlow<UiState<Student>> =
        MutableStateFlow(UiState(state = State.None))
    val uiState = _uiState.asStateFlow()

    private val _rosterUiState: MutableStateFlow<UiState<StudentRoster>> =
        MutableStateFlow(UiState(state = State.None))
    val rosterUiState = _rosterUiState.asStateFlow()

    private val _studentList: MutableStateFlow<MutableList<Student>> =
        MutableStateFlow(mutableListOf())
    val studentList = _studentList.asStateFlow()

    private val _studentRosterList: MutableStateFlow<MutableList<StudentRoster>> =
        MutableStateFlow(mutableListOf())
    val studentRosterList = _studentRosterList.asStateFlow()
    private val _studentRosterListMonth: MutableStateFlow<MutableList<StudentRoster>> =
        MutableStateFlow(mutableListOf())
    val studentRosterListMonth = _studentRosterListMonth.asStateFlow()
    private val _studentRosterListWeek: MutableStateFlow<MutableList<StudentRoster>> =
        MutableStateFlow(mutableListOf())
    val studentRosterListWeek = _studentRosterListWeek.asStateFlow()

    fun getStudents() {
        viewModelScope.launch {
            _uiState.tryEmit(UiState())
            studentLists.get().collect {
                when (val result = it) {
                    is Response.Success -> {
                        _studentList.tryEmit(result.data!!)

                        _uiState.tryEmit(
                            UiState(State.Success, dataList = result.data, msg = result.msg)
                        )
                    }
                    else -> {
                        _uiState.tryEmit(UiState(state = State.Error, msg = result.msg))
                    }
                }
            }
        }
    }

    fun create() = viewModelScope.launch {
        _uiState.tryEmit(UiState(msg = "Creating"))
        when (val result = studentLists.create(student.apply {
            parent = mutableListOf(this@StudentsVM.parent)
        })) {
            is Response.Success -> {
                _uiState.tryEmit(
                    UiState(State.Success, msg = result.msg)
                )
            }
            else -> {
                _uiState.tryEmit(UiState(state = State.Error, msg = result.msg))
            }
        }
    }

    fun filterList(item: String, list: MutableList<Student>) = viewModelScope.launch {
        _uiState.tryEmit(UiState())
        try {
            val modifiedList = if (item.equals("all", true)) list else list.filter {
                it.classSession == item
            }.toMutableList()
            _uiState.tryEmit(
                UiState(
                    state = if (modifiedList.isEmpty()) State.Error else State.Success,
                    dataList = modifiedList,
                    msg = if (modifiedList.isEmpty()) "No item found for \"$item\" class" else null,
                )
            )
        } catch (e: Exception) {
            _uiState.tryEmit(UiState(state = State.Error, msg = e.message))
        }
    }

    fun filterRosterList(
        monthIndex: Int, month: String, list: MutableList<StudentRoster>
    ) = viewModelScope.launch {
        Log.e(TAG, "filterRosterList Requested: $monthIndex --> $month\n\n\n")
        _uiState.tryEmit(UiState())
        try {
            val modifiedList = list.filter {
                Log.e(TAG, "filterRosterList: Filter month ${it.monthDetail}")
                Log.e(
                    TAG, "filterRosterList: Check equals ($monthIndex -> ${it.monthDetail.month})"
                )
                it.monthDetail.month == monthIndex
            }.toMutableList()
            _studentRosterListMonth.tryEmit(modifiedList)
            Log.e(TAG, "filterRosterList: Data")
            _rosterUiState.tryEmit(
                UiState(
                    state = if (modifiedList.isEmpty()) State.Error else State.Success,
                    msg = if (modifiedList.isEmpty()) "No item found for \"$month\" class" else null,
                )
            )
        } catch (e: Exception) {
            _uiState.tryEmit(UiState(state = State.Error, msg = e.message))
        }
    }

    fun getRoster() = viewModelScope.launch {
        _rosterUiState.tryEmit(UiState())
        studentLists.roster().collect {
            when (val result = it) {
                is Response.Success -> {
                    _rosterUiState.tryEmit(
                        UiState(State.Success, dataList = result.data!!, msg = result.msg)
                    )
                }
                else -> {
                    _rosterUiState.tryEmit(UiState(state = State.Error, msg = result.msg))
                }
            }
        }
    }
}