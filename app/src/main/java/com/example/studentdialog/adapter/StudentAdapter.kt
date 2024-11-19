package com.example.studentdialog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentdialog.R
import com.example.studentdialog.model.Student

class StudentAdapter(
    private var students: List<Student>,
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    inner class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvStudentId: TextView = view.findViewById(R.id.tvStudentId)
        private val tvStudentName: TextView = view.findViewById(R.id.tvStudentName)

        fun bind(student: Student) {
            tvStudentId.text = itemView.context.getString(R.string.student_id, student.id)
            tvStudentName.text = itemView.context.getString(R.string.student_name, student.name)
            itemView.findViewById<Button>(R.id.button_edit).setOnClickListener{ onEditClick(student) }
            itemView.findViewById<Button>(R.id.button_delete).setOnClickListener{ onDeleteClick(student) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position])
    }

    override fun getItemCount() = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
}