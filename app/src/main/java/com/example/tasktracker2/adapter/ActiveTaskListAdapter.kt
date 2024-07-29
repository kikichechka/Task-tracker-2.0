package com.example.tasktracker2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker2.R
import com.example.tasktracker2.databinding.ActiveItemTaskBinding
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel
import java.time.format.DateTimeFormatter

class ActiveTaskListAdapter : ListAdapter<TaskModel, ActiveViewHolder>(DiffUtilCallbackTask()) {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun onBindViewHolder(holder: ActiveViewHolder, position: Int) {
        val item = getItem(position)
        showActiveTask(item, holder.binding)
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun showActiveTask(item: TaskModel?, binding: ActiveItemTaskBinding) {
        with(binding) {
            deadline.text = if (item?.completionDate != null)
                "${item.completionDate?.format(formatter)} / ${item.completionTime?.toString()}" else "- / -"
            when (item?.importance) {
                ImportanceModel.LOW -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.green
                        )
                    )
                    importance.text = ImportanceModel.LOW.str
                }

                ImportanceModel.MEDIUM -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.orange
                        )
                    )
                    importance.text = ImportanceModel.MEDIUM.str
                }

                ImportanceModel.HIGH -> {
                    cardImportance.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveViewHolder {
        return ActiveViewHolder(ActiveItemTaskBinding.inflate(LayoutInflater.from(parent.context)))
    }

    companion object {
        private const val MAX_LINES = 20
    }
}

class ActiveViewHolder(val binding: ActiveItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

fun TextView.isEllipsized() = layout.text.toString() != text.toString()
