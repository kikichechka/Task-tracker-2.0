package com.example.tasktracker2.fragments.active

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
import com.example.tasktracker2.fragments.adapter.AllTaskListAdapter
import com.example.tasktracker2.model.ActivityModel
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class ActiveTasksFragment : Fragment() {
    private var _binding: FragmentActiveTasksBinding? = null
    private val binding: FragmentActiveTasksBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory

    private val viewModel: ActiveTasksViewModel by viewModels { viewModelsFactory }
    private val allTaskListAdapter = AllTaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveTasksBinding.inflate(inflater)
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

        binding.buttonAddNew.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.add(
                    TaskModel(
                        importance = ImportanceModel.HIGH,
                        activity = ActivityModel.COMPLETED,
                        startDate = LocalDate.now(),
                        startTime = LocalTime.of(10, 10),
                        completionDate = LocalDate.now(),
                        completionTime = LocalTime.of(20, 20),
                        title = "Title",
                        description = "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription"
                    )
                )
                viewModel.updateData()
            }
        }
    }
}
