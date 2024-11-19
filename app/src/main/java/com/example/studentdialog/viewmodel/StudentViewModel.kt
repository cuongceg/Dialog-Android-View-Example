package com.example.studentdialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentdialog.model.Student

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    init {
        _students.value = listOf(
            Student(20225172,"Nguyễn Văn A"),
            Student(20225173,"Nguyễn Văn B"),
            Student(20225174,"Nguyễn Văn C"),
            Student(20225175,"Nguyễn Văn D"),
        )
    }

    fun addStudent(student: Student) {
        val newStudents = _students.value?.toMutableList() ?: mutableListOf()
        newStudents.add(student)
        _students.value = newStudents
    }

    fun updateInformationStudent(student: Student) {
        val newStudents = _students.value?.toMutableList() ?: mutableListOf()
        val index = newStudents.indexOfFirst { it.id == student.id }
        if (index != -1) {
            newStudents[index] = student
            _students.value = newStudents
        }
    }

    fun deleteStudent(student: Student) {
        val newStudents = _students.value?.toMutableList() ?: mutableListOf()
        newStudents.remove(student)
        _students.value = newStudents
    }
}