package com.example.tasktracker2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tasktracker2.databinding.FragmentMainTaskBinding
import com.example.tasktracker2.model.StateTask
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainTaskFragment : Fragment() {
    private var _binding: FragmentMainTaskBinding? = null
    private val binding: FragmentMainTaskBinding
        get() = _binding!!

    private val viewModel: MainTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.choiceData.collect {
                when (it) {
                    StateTask.ACTIVE -> {
                        binding.activeButton.isEnabled = false
                        binding.completedButton.isEnabled = true
                        binding.topAppBar.menu.setGroupVisible(0, false)
                        childFragmentManager.commit {
                            replace(R.id.container_for_fragment, ActiveTasksFragment())
                        }
                    }

                    StateTask.COMPLETED -> {
                        binding.activeButton.isEnabled = true
                        binding.completedButton.isEnabled = false
                        binding.topAppBar.menu.setGroupVisible(0, true)
                        childFragmentManager.commit {
                            replace(R.id.container_for_fragment, CompletedTasksFragment())
                        }
                    }
                }
                checkEnabledButton()
            }
        }

        binding.activeButton.setOnClickListener {
            viewModel.changeChoice()
            checkEnabledButton()
        }

        binding.completedButton.setOnClickListener {
            viewModel.changeChoice()
            checkEnabledButton()
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_button_add_new -> {

                    true
                }

                else -> false
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun checkEnabledButton() {
        when (binding.activeButton.isEnabled) {
            true -> binding.activeButton.setTextAppearance(R.style.style_button_disabled)
            false -> binding.activeButton.setTextAppearance(R.style.style_button_enabled)
        }

        when (binding.completedButton.isEnabled) {
            true -> binding.completedButton.setTextAppearance(R.style.style_button_disabled)
            false -> binding.completedButton.setTextAppearance(R.style.style_button_enabled)
        }
    }
}
