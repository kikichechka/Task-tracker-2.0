package com.example.tasktracker2.fragments.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tasktracker2.R
import com.example.tasktracker2.ViewModelsFactory
import com.example.tasktracker2.databinding.FragmentActiveTasksBinding
import com.example.tasktracker2.databinding.FragmentCreateNewTaskBinding
import com.example.tasktracker2.fragments.active.ActiveTasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewTaskFragment : Fragment() {
    private var _binding: FragmentCreateNewTaskBinding? = null
    private val binding: FragmentCreateNewTaskBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory

    private val viewModel: CreateNewTaskViewModel by viewModels { viewModelsFactory }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNewTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setTextTitle(getString(R.string.start))
        binding.completion.setTextTitle(getString(R.string.completion))
    }
}