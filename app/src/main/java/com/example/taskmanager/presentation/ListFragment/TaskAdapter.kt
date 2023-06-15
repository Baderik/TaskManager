package com.example.taskmanager.presentation.ListFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ItemTaskBinding

class TaskAdapter(private val hostListener: TaskListener): RecyclerView.Adapter<TaskAdapter.TasksViewHolder>() {

    private var tasks: MutableList<Task> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTaskBinding.bind(view)
        fun bind(task: Task){
            binding.apply {
                title.text = task.title
                description.text = task.description
                isSuccessCheckBox.isChecked = task.isSuccess
                val imageRes = if (task.isSelected) R.drawable.baseline_star_24 else R.drawable.baseline_star_border_24
                isSelectedButton.setImageResource(imageRes)
                if (task.description.isBlank())
                    description.visibility = View.GONE


                isSelectedButton.setOnClickListener{
                    hostListener.onSelectedPress(task)
                }


                isSuccessCheckBox.setOnClickListener{
                    task.isSuccess = !task.isSuccess
                    hostListener.onSuccessPress(task)
                }

                deleteButton.setOnClickListener{
                    hostListener.onDeletePress(task)
                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks.toMutableList()
        notifyDataSetChanged()
    }
}