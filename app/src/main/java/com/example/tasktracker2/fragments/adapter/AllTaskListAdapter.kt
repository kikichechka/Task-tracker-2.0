package com.example.tasktracker2.fragments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker2.R
import com.example.tasktracker2.databinding.ActiveItemTaskBinding
import com.example.tasktracker2.databinding.CompletedItemTaskBinding
import com.example.tasktracker2.model.ActivityModel
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel

class AllTaskListAdapter : ListAdapter<TaskModel, BaseViewHolder>(DiffUtilCallbackTask()) {
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ActiveViewHolder -> showActiveTask(item, holder.binding)

            is CompletedViewHolder -> showCompletedTask(item, holder.binding)
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun showActiveTask(item: TaskModel?, binding: ActiveItemTaskBinding) {
        with(binding) {
            deadline.text = "${item?.startDate.toString()} / ${item?.startTime.toString()}"
            when (item?.importance) {
                ImportanceModel.LOW -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    importance.text = ImportanceModel.LOW.str
                }

                ImportanceModel.MEDIUM -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.orange))
                    importance.text = ImportanceModel.MEDIUM.str
                }

                ImportanceModel.HIGH -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    importance.text = ImportanceModel.HIGH.str
                }

                null -> {}
            }
            title.text = item?.title
            description.text = item?.description
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun showCompletedTask(item: TaskModel?, binding: CompletedItemTaskBinding) {
        with(binding) {
            deadline.text = "${item?.startDate.toString()} / ${item?.startTime.toString()}"
            when (item?.importance) {
                ImportanceModel.LOW -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.green))
                    importance.text = ImportanceModel.LOW.str
                }

                ImportanceModel.MEDIUM -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.orange))
                    importance.text = ImportanceModel.MEDIUM.str
                }

                ImportanceModel.HIGH -> {
                    cardImportance.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.red))
                    importance.text = ImportanceModel.HIGH.str
                }

                null -> {}
            }
            title.text = item?.title
            description.text = item?.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ActivityModel.ACTIVE.ordinal -> {
                ActiveViewHolder(ActiveItemTaskBinding.inflate(LayoutInflater.from(parent.context)))
            }

            ActivityModel.COMPLETED.ordinal -> {
                CompletedViewHolder(CompletedItemTaskBinding.inflate(LayoutInflater.from(parent.context)))
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.activity) {
            ActivityModel.ACTIVE -> ActivityModel.ACTIVE.ordinal
            ActivityModel.COMPLETED -> ActivityModel.COMPLETED.ordinal
            null -> ActivityModel.ACTIVE.ordinal
        }
    }
}

sealed class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class ActiveViewHolder(val binding: ActiveItemTaskBinding) : BaseViewHolder(binding.root)

class CompletedViewHolder(val binding: CompletedItemTaskBinding) : BaseViewHolder(binding.root)