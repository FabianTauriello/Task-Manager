package io.github.fabiantauriello.listmaker

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class TaskListTasksFragment : Fragment() {

    lateinit var taskList: TaskList
    lateinit var listDataManager: ListDataManager
    lateinit var rvTaskList: RecyclerView
    lateinit var btnAddTask: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataManager = ViewModelProviders.of(this).get(ListDataManager::class.java)
        // retrieve data from nav graph action
        arguments?.let {
            val args = TaskListTasksFragmentArgs.fromBundle(it)
            // retrieve only the tasks with the specified name from the action argument
            taskList = listDataManager.readLists().filter { list -> list.name == args.listString }[0]
        }

        activity?.let {
            rvTaskList = view.findViewById(R.id.rv_tasks)
            rvTaskList.layoutManager = LinearLayoutManager(it)
            rvTaskList.adapter = TaskListTasksAdapter(taskList)
            it.toolbar?.title = taskList.name

            btnAddTask = view.findViewById(R.id.btn_add_task)
            btnAddTask.setOnClickListener {
                showCreateTaskDialog()
            }
        }

    }

    private fun showCreateTaskDialog() {
        activity?.let {
            val taskEditText = EditText(it)
            taskEditText.inputType = InputType.TYPE_CLASS_TEXT
            AlertDialog.Builder(it)
                .setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task) { dialog, _ ->
                    val task = taskEditText.text.toString()
                    taskList.tasks.add(task)
                    listDataManager.saveList(taskList)
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

}
