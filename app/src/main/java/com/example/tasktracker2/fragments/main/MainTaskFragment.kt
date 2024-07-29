package com.example.tasktracker2.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker2.R
import com.example.tasktracker2.ViewModelsFactory
import com.example.tasktracker2.adapter.ActiveTaskListAdapter
import com.example.tasktracker2.databinding.FragmentMainTaskBinding
import com.example.tasktracker2.adapter.CompletedTaskListAdapter
import com.example.tasktracker2.fragments.change.ChangeTaskFragment
import com.example.tasktracker2.helper.MyButton
import com.example.tasktracker2.helper.MySwipeHelper
import com.example.tasktracker2.listener.MyButtonClickListener
import com.example.tasktracker2.model.StateTask
import com.example.tasktracker2.model.TaskModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainTaskFragment : Fragment() {
    private var _binding: FragmentMainTaskBinding? = null
    private val binding: FragmentMainTaskBinding
        get() = _binding!!

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel: MainTaskViewModel by viewModels { viewModelsFactory }
    private val activeTaskListAdapter = ActiveTaskListAdapter()
    private val completedTaskListAdapter = CompletedTaskListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipe = object : MySwipeHelper(requireContext(), binding.recyclerView, BUTTON_WIDTH) {
            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(
                        getString(R.string.delete),
                        TEXT_SIZE,
                        resources.getColor(R.color.grey, null),
                        object : MyButtonClickListener {
                            override fun onClick(position: Int) {
                                viewLifecycleOwner.lifecycleScope.launch {
                                    viewModel.deleteTask(position)
                                }
                            }
                        })
                )
                buffer.add(
                    MyButton(
                        getString(R.string.change),
                        TEXT_SIZE,
                        resources.getColor(R.color.grey, null),
                        object : MyButtonClickListener {
                            override fun onClick(position: Int) {
                                val taskModel = viewModel.getTaskByPosition(position)
                                val bundle = Bundle().apply {
                                    putParcelable(ChangeTaskFragment.ARG_PARAM_TASK, taskModel)
                                }
                                findNavController().navigate(
                                    R.id.action_mainTaskFragment_to_changeTaskFragment,
                                    bundle
                                )
                            }
                        })
                )
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateListTasks()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.choiceData.collect {
                binding.recyclerView.visibility = View.GONE
                switchListTasks(it)
                checkEnabledButton()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listData.collect {
                switchListTasks(viewModel.choiceData.value)
            }
        }

        binding.buttonAddNew.setOnClickListener {
            findNavController().navigate(R.id.action_mainTaskFragment_to_createNewTaskFragment)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_button_add_new -> {
                    findNavController().navigate(R.id.action_mainTaskFragment_to_createNewTaskFragment)
                    true
                }

                else -> false
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
                    findNavController().navigate(R.id.action_mainTaskFragment_to_createNewTaskFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun switchListTasks(stateTask: StateTask) {
        when (stateTask) {
            StateTask.ACTIVE -> {
                binding.activeButton.isEnabled = false
                binding.completedButton.isEnabled = true
                val listActive = viewModel.getListActiveTasks()
                activeTaskListAdapter.submitList(listActive)
                binding.recyclerView.adapter = activeTaskListAdapter
                checkList(listActive)
            }

            StateTask.COMPLETED -> {
                binding.activeButton.isEnabled = true
                binding.completedButton.isEnabled = false

                val listCompleted = viewModel.getListCompletedTasks()
                completedTaskListAdapter.submitList(listCompleted)
                binding.recyclerView.adapter = completedTaskListAdapter
                checkList(listCompleted)
            }
        }
    }

    private fun checkList(list: List<TaskModel>?) {
        with(binding) {
            if (list != null) {
                if (list.isEmpty()) {
                    topAppBar.menu.setGroupVisible(0, false)
                    recyclerView.visibility = View.GONE
                    buttonAddNew.visibility = View.VISIBLE
                    image.visibility = View.VISIBLE
                } else {
                    image.visibility = View.GONE
                    topAppBar.menu.setGroupVisible(0, true)
                    buttonAddNew.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
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

    companion object {
        private const val BUTTON_WIDTH = 250
        private const val TEXT_SIZE = 35
    }
}
