package com.example.tasktracker2.fragments.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tasktracker2.R
import com.example.tasktracker2.ViewModelsFactory
import com.example.tasktracker2.databinding.FragmentActiveTasksBinding
import com.example.tasktracker2.databinding.FragmentCompletedTasksBinding
import com.example.tasktracker2.fragments.active.ActiveTasksViewModel
import com.example.tasktracker2.fragments.adapter.AllTaskListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CompletedTasksFragment : Fragment() {
    private var _binding: FragmentCompletedTasksBinding? = null
    private val binding: FragmentCompletedTasksBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory

    private val viewModel: CompletedTasksViewModel by viewModels { viewModelsFactory }
    private val allTaskListAdapter = AllTaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompletedTasksBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateData()
        }
        binding.recyclerView.adapter = allTaskListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listData.collect {
                it?.let {
                    if (it.isEmpty()) {
                        Glide.with(requireContext())
                            .load(R.drawable.group)
                            .fitCenter()
                            .into(binding.image)
                    } else {
                        binding.image.visibility = View.GONE
                        allTaskListAdapter.submitList(it)
                    }
                }
            }
        }
    }
}