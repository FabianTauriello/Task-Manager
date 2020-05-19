package io.github.fabiantauriello.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListTasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTask = itemView.findViewById(R.id.tv_task) as TextView
}