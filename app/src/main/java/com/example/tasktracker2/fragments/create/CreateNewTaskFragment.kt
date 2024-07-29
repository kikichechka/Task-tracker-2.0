package com.example.tasktracker2.fragments.create

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tasktracker2.R
import com.example.tasktracker2.ViewModelsFactory
import com.example.tasktracker2.databinding.FragmentCreateNewTaskBinding
import com.example.tasktracker2.model.ImportanceModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewTaskFragment : Fragment() {
    private var _binding: FragmentCreateNewTaskBinding? = null
    private val binding: FragmentCreateNewTaskBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel: CreateNewTaskViewModel by viewModels { viewModelsFactory }
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
    private var importanceModel = ImportanceModel.LOW
    private var startDate: LocalDate? = LocalDate.now()
    private var startTime: LocalTime? = LocalTime.now()
    private var completionDate: LocalDate? = LocalDate.now()
    private var completionTime: LocalTime? = LocalTime.now().plusMinutes(1)
    private var title: String = ""
    private var description: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewTaskBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.custom.binding.startDate.text = startDate?.format(formatter)
        binding.custom.binding.startTime.text = startTime?.format(formatterTime)
        binding.custom.binding.completeDate.text = completionDate?.format(formatter)
        binding.custom.binding.completeTime.text = completionTime?.format(formatterTime)


        checkingCompletedData()
        followTextTitle()
        followTextDescription()
        with(binding.custom.binding) {
            buttonLow.setTextAppearance(R.style.style_text_button_choose_importance)
            buttonLow.setOnClickListener {
                removeButtonStyles()
                buttonLow.setStyleSelectedButton()
                importanceModel = ImportanceModel.LOW
            }
            buttonHigh.setOnClickListener {
                removeButtonStyles()
                buttonHigh.setStyleSelectedButton()
                importanceModel = ImportanceModel.HIGH
            }
            buttonMedium.setOnClickListener {
                removeButtonStyles()
                buttonMedium.setStyleSelectedButton()
                importanceModel = ImportanceModel.MEDIUM
            }
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

        buttonNavigationClick()
        buttonAddNewClick()
    }

    private fun buttonNavigationClick() {
        binding.topAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun buttonAddNewClick() {
        binding.buttonAddNew.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.saveNewTask(
                    importanceModel,
                    startDate,
                    startTime,
                    completionDate,
                    completionTime,
                    title,
                    description
                )
            }
            Snackbar.make(it, "${getString(R.string.create_task)}", 60000)
                .setIcon(
                    getDrawable(requireContext(), R.drawable.baseline_close_24)!!,
                    resources.getColor(android.R.color.white, null)
                )
                .setActionTextColor(resources.getColor(R.color.white, null))
                .setBackgroundTint(resources.getColor(R.color.black, null))
                .show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun removeButtonStyles() {
        with(binding.custom.binding) {
            buttonLow.removeButtonStyles()
            buttonHigh.removeButtonStyles()
            buttonMedium.removeButtonStyles()
        }
    }

    private fun showStartCalendar() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, day: Int ->
                with(binding.custom) {
                    startDate = LocalDate.of(year, month + 1, day)
                    binding.startDate.text = startDate?.format(formatter)
                    if (completionDate!! < startDate) {
                        completionDate = startDate
                        binding.completeDate.text = completionDate?.format(formatter)
                    }
                }
            },
            startDate!!.year,
            startDate!!.monthValue - 1,
            startDate!!.dayOfMonth
        )
        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }

    private fun showCompletionCalendar() {
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, day: Int ->
                with(binding.custom) {
                    completionDate = LocalDate.of(year, month + 1, day)
                    binding.completeDate.text = completionDate?.format(formatter)
                }

            },
            completionDate!!.year,
            completionDate!!.monthValue - 1,
            completionDate!!.dayOfMonth
        )
        val calendar = Calendar.getInstance()
        calendar.set(startDate!!.year, startDate!!.monthValue - 1, startDate!!.dayOfMonth)
        datePicker.datePicker.minDate = calendar.timeInMillis
        datePicker.show()
    }

    private fun showStartTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalTime.now().hour)
                .setMinute(LocalTime.now().minute)
                .setTitleText(startDate.toString())
                .build()
        picker.show(childFragmentManager, "")
        picker.addOnPositiveButtonClickListener {
            startTime = LocalTime.of(picker.hour, picker.minute)
            binding.custom.binding.startTime.text = startTime.toString()
        }
    }

    private fun showCompletionTimePicker() {
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(LocalTime.now().hour)
                .setMinute(LocalTime.now().minute)
                .setTitleText(startDate.toString())
                .build()
        picker.show(childFragmentManager, "")
        picker.addOnPositiveButtonClickListener {
            with(binding.custom) {
                completionTime = LocalTime.of(picker.hour, picker.minute)
                binding.completeTime.text = completionTime.toString()
            }
        }
    }

    private fun followTextTitle() {
        binding.custom.binding.taskTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                title = p0.toString()
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
                description = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun checkingCompletedData() {
        binding.buttonAddNew.isEnabled = title.isNotEmpty()

        when (binding.buttonAddNew.isEnabled) {
            true -> binding.buttonAddNew.setTextAppearance(R.style.style_button_enabled)
            false -> binding.buttonAddNew.setTextAppearance(R.style.style_button_disabled)
        }
    }
}

fun MaterialButton.setStyleSelectedButton() {
    this.setTextAppearance(R.style.style_text_button_choose_importance)
}

fun MaterialButton.removeButtonStyles() {
    this.setTextAppearance(R.style.style_text_button_importance)
}

fun Snackbar.setIcon(drawable: Drawable, @ColorInt colorTint: Int): Snackbar {
    return this.apply {
        setAction(" ") {}
        val textView = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.text = ""

        drawable.setTint(colorTint)
        drawable.setTintMode(PorterDuff.Mode.SRC_ATOP)
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}
