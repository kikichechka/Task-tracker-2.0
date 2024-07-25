package com.example.tasktracker2.fragments

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.tasktracker2.R
import com.example.tasktracker2.databinding.CustomDateViewBinding

class CustomDateView(
    context: Context,
    attributeSet: AttributeSet? = null
): ConstraintLayout(context, attributeSet){
    private val binding: CustomDateViewBinding

    init {
        val inflatedView = inflate(context, R.layout.custom_date_view, this)
        binding = CustomDateViewBinding.bind(inflatedView)
    }

    fun setTextTitle(text: String) {
        binding.title.text = text
    }
}