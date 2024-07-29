package com.example.tasktracker2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker2.R
import com.example.tasktracker2.databinding.CompletedItemTaskBinding
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel
import java.time.format.DateTimeFormatter

class CompletedTaskListAdapter :
    ListAdapter<TaskModel, CompletedViewHolder>(DiffUtilCallbackTask()) {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        return CompletedViewHolder(CompletedItemTaskBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CompletedViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            deadline.text = if (item?.completionDate != null)
                "${item.completionDate?.format(formatter)} / ${item.completionTime?.toString()}" else "- / -"

            when (item?.importance) {
                ImportanceModel.LOW -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.green
                        )
                    )
                    importance.text = ImportanceModel.LOW.str
                }

                ImportanceModel.MEDIUM -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.orange
                        )
                    )
                    importance.text = ImportanceModel.MEDIUM.str
                }

                ImportanceModel.HIGH -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.red
                        )
                    )
                    importance.text = ImportanceModel.HIGH.str
                }

                null -> {}
            }
            title.text = item?.title
            description.text = item?.description
            root.setOnClickListener {
                if (description.isEllipsized()) {
                    description.maxLines = MAX_LINES
                }
            }
        }
    }

    companion object {
        private const val MAX_LINES = 20
    }
}

class CompletedViewHolder(val binding: CompletedItemTaskBinding) :
    RecyclerView.ViewHolder(binding.root)
