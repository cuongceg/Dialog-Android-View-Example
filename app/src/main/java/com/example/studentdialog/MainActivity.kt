package com.example.studentdialog

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdialog.adapter.StudentAdapter
import com.example.studentdialog.model.Student
import com.example.studentdialog.viewmodel.StudentViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var studentAdapter: StudentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(
            students = listOf(),
            onEditClick = { student -> showEditDialog(student) },
            onDeleteClick = { student -> showDeleteDialog(student) }
        )

        recyclerView.adapter = studentAdapter

        studentViewModel.students.observe(this){ students ->
            studentAdapter.updateData(students)
        }
        findViewById<Button>(R.id.extended_fab).setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog() {
        val dialogView = layoutInflater.inflate(R.layout.layout_dialog, null)
        val etName = dialogView.findViewById<EditText>(R.id.edit_name)
        val etId = dialogView.findViewById<EditText>(R.id.edit_id)

        AlertDialog.Builder(this)
            .setTitle("Add Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString()
                val id = etId.text.toString().toIntOrNull()
                if (name.isNotBlank() && id != null) {
                    if(studentViewModel.students.value?.find { it.id == id } == null){
                        studentViewModel.addStudent(Student(id, name))
                    }
                    else{
                        Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(student: Student) {
        val dialogView = layoutInflater.inflate(R.layout.layout_dialog, null)
        val etName = dialogView.findViewById<EditText>(R.id.edit_name)
        val etId = dialogView.findViewById<EditText>(R.id.edit_id)
        etName.setText(student.name)
        etId.setText(student.id.toString())

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val newName = etName.text.toString()
                val newId = etId.text.toString().toIntOrNull()
                if (newName.isNotBlank() && newId != null) {
                    studentViewModel.updateInformationStudent(student.copy(name = newName, id = newId))
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteDialog(student: Student) {
        AlertDialog.Builder(this)
            .setMessage("Do you want to delete student ${student.name}?")
            .setPositiveButton("Delete") { _, _ ->
                studentViewModel.deleteStudent(student)
                Snackbar.make(findViewById(R.id.main), "${student.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        studentViewModel.addStudent(student)
                    }.show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


}