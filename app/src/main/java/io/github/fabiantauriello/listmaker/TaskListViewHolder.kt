package io.github.fabiantauriello.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var listPositionTextView: TextView = itemView.findViewById(R.id.tv_task_list_number)
    var listTitleTextView: TextView = itemView.findViewById(R.id.tv_task_list_title)

}