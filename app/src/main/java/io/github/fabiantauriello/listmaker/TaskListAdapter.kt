package io.github.fabiantauriello.listmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(
    private val taskLists: ArrayList<TaskList>,
    private val clickListener: ToDoListClickListener
) : RecyclerView.Adapter<TaskListViewHolder>() {

    interface ToDoListClickListener {
        fun listItemClicked(taskList: TaskList)
    }

    // Creates a new View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list_view_holder, parent, false)
        return TaskListViewHolder(view)
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return taskLists.size
    }

    // Customizes the view - called for each row in the list
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.listPositionTextView.text = (position + 1).toString()
        holder.listTitleTextView.text = taskLists[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(taskLists[position])
        }
    }

    fun addList(list: TaskList) {
        taskLists.add(list)
        notifyItemInserted(taskLists.size - 1)
    }

}