package com.example.tasktracker2.fragments.change

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tasktracker2.R
import com.example.tasktracker2.ViewModelsFactory
import com.example.tasktracker2.databinding.CustomTaskBinding
import com.example.tasktracker2.databinding.FragmentChangeTaskBinding
import com.example.tasktracker2.fragments.create.removeButtonStyles
import com.example.tasktracker2.fragments.create.setStyleSelectedButton
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ChangeTaskFragment : Fragment() {
    private var paramTask: TaskModel? = null

    private var _binding: FragmentChangeTaskBinding? = null
    private val binding: FragmentChangeTaskBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel: ChangeTaskViewModel by viewModels { viewModelsFactory }
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            if (it != null) {
                paramTask = if (SDK_INT >= 33)
                    it.getParcelable(ARG_PARAM_TASK, TaskModel::class.java)
                else
                    it.getParcelable(ARG_PARAM_TASK)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paramTask?.let {
            with(binding.custom.binding) {
                createStyleButtons(it, this)
                clickImportanceButton(this)
                setValues(this)
                followTextDescription()
            }
        }
        clickButtonsFragment()
        checkingCompletedData()
        with(binding.custom.binding) {
            startDate.setOnClickListener {
                showStartCalendar()
            }
            startTime.setOnClickListener {
                showStartTimePicker()
            }
            completeDate.setOnClickListener {
                showCompletionCalendar()
            }
            completeTime.setOnClickListener {
                showCompletionTimePicker()
            }
        }
        followTextTitle()
    }

    private fun clickButtonsFragment() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }

            buttonAddNew.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    paramTask?.let {
                        viewModel.changeTask(it)
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    private fun setValues(customTaskBinding: CustomTaskBinding) {
        with(customTaskBinding) {
            startDate.text = paramTask?.startDate?.format(formatter)
            startTime.text = paramTask?.startTime.toString()
            completeDate.text = paramTask?.completionDate?.format(formatter)
            completeTime.text = paramTask?.completionTime.toString()
            taskTitle.setText(paramTask?.title)
            taskDescription.setText(paramTask?.description)
        }
    }

    private fun createStyleButtons(taskModel: TaskModel, customTaskBinding: CustomTaskBinding) {
        when (taskModel.importance) {
            ImportanceModel.LOW -> customTaskBinding.buttonLow.setTextAppearance(R.style.style_text_button_choose_importance)
            ImportanceModel.MEDIUM -> customTaskBinding.buttonMedium.setTextAppearance(R.style.style_text_button_choose_importance)
            ImportanceModel.HIGH -> customTaskBinding.buttonHigh.setTextAppearance(R.style.style_text_button_choose_importance)
        }
    }

    private fun clickImportanceButton(customTaskBinding: CustomTaskBinding) {
        customTaskBinding.buttonLow.setOnClickListener {
            removeButtonStyles()
            customTaskBinding.buttonLow.setStyleSelectedButton()
        }
        customTaskBinding.buttonHigh.setOnClickListener {
            removeButtonStyles()
            customTaskBinding.buttonHigh.setStyleSelectedButton()
        }
        customTaskBinding.buttonMedium.setOnClickListener {
            removeButtonStyles()
            customTaskBinding.buttonMedium.setStyleSelectedButton()
        }
    }

    private fun showStartCalendar() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, day: Int ->
                paramTask?.startDate = LocalDate.of(year, month + 1, day)
                binding.custom.binding.startDate.text = paramTask?.startDate?.format(formatter)
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
        datePicker.show()
    }

    private fun showCompletionCalendar() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, day: Int ->
                paramTask?.completionDate = LocalDate.of(year, month + 1, day)
                binding.custom.binding.completeDate.text =
                    paramTask?.completionDate?.format(formatter)
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
        datePicker.show()
    }

    private fun showStartTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalTime.now().hour)
                .setMinute(LocalTime.now().minute)
                .setTitleText(paramTask?.startTime.toString())
                .build()
        picker.show(childFragmentManager, "")
        picker.addOnPositiveButtonClickListener {
            paramTask?.startTime = LocalTime.of(picker.hour, picker.minute)
            binding.custom.binding.startTime.text = paramTask?.startTime.toString()
        }
    }

    private fun showCompletionTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalTime.now().hour)
                .setMinute(LocalTime.now().minute)
                .setTitleText(paramTask?.startDate?.format(formatter))
                .build()
        picker.show(childFragmentManager, "")
        picker.addOnPositiveButtonClickListener {
            paramTask?.completionTime = LocalTime.of(picker.hour, picker.minute)
            binding.custom.binding.completeTime.text = paramTask?.completionTime.toString()
        }
    }

    private fun followTextTitle() {
        binding.custom.binding.taskTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                paramTask?.title = p0.toString()
                checkingCompletedData()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun followTextDescription() {
        binding.custom.binding.taskDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                paramTask?.description = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun removeButtonStyles() {
        with(binding.custom.binding) {
            buttonLow.removeButtonStyles()
            buttonHigh.removeButtonStyles()
            buttonMedium.removeButtonStyles()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkingCompletedData() {
        paramTask?.title?.isNotEmpty()?.let {
            binding.buttonAddNew.isEnabled = it

            when (binding.buttonAddNew.isEnabled) {
                true -> binding.buttonAddNew.setTextAppearance(R.style.style_button_enabled)
                false -> binding.buttonAddNew.setTextAppearance(R.style.style_button_disabled)
            }
        }
    }

    companion object {
        const val ARG_PARAM_TASK = "ARG_PARAM_TASK"
    }
}
