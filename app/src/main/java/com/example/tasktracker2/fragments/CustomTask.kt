package com.example.tasktracker2.fragments

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tasktracker2.R
import com.example.tasktracker2.databinding.CustomTaskBinding

class CustomTask(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {
    val binding: CustomTaskBinding

    init {
        val inflatedView = inflate(context, R.layout.custom_task, this)
        binding = CustomTaskBinding.bind(inflatedView)
    }
}
