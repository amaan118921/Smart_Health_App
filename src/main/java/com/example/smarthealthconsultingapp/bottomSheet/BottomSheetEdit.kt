package com.example.smarthealthconsultingapp.bottomSheet

import android.content.Context
import android.widget.EditText
import com.example.smarthealthconsultingapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView

class BottomSheetEdit(context: Context): BottomSheetDialog(context)  {

    private lateinit var et: EditText
    private lateinit var btnSave: MaterialTextView
    private lateinit var btnCancel: MaterialTextView

    fun init(layoutId: Int) {
        setContentView(layoutId)
        dismissWithAnimation = true
        et = findViewById(R.id.et)!!
        btnSave = findViewById(R.id.btnSave)!!
        btnCancel = findViewById(R.id.btnCancel)!!
        setListeners(layoutId)
    }

    private fun setListeners(layoutId: Int) {
        btnCancel.setOnClickListener {
            dismissBottom()
        }
        btnSave
    }

    fun dismissBottom() {
        dismiss()
    }

    fun showBottom() {
        show()
    }

}