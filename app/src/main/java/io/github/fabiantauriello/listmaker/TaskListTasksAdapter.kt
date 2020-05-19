package io.github.fabiantauriello.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskListTasksAdapter(var taskList: TaskList) : RecyclerView.Adapter<TaskListTasksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListTasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_view_holder,  parent, false)
        return TaskListTasksViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

    override fun onBindViewHolder(holder: TaskListTasksViewHolder, position: Int) {
        holder.tvTask.text = taskList.tasks[position]
    }

}