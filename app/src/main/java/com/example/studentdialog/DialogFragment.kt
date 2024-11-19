package com.example.studentdialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class DialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.layout_dialog)
        val editName = dialog.findViewById<EditText>(R.id.edit_hoten)
        val editID = dialog.findViewById<EditText>(R.id.edit_mssv)
        dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
            val name = editName.text.toString()
            val id = editID.text.toString()
            Log.v("TAG", "$name - $id")
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return dialog
    }
}