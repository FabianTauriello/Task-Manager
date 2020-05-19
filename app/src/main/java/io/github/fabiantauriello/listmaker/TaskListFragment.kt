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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(), TaskListAdapter.ToDoListClickListener {

    // lateinit allows initializing a not-null property outside of a constructor.
    // it indicates that the recycler view will be initialized some time in the future instead of
    // when the activity is created. Without it, I would have to initialize it right now.
    private lateinit var rvToDoList: RecyclerView
    private lateinit var listDataManager: ListDataManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    // this is called immediately after above method (onCreateView())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            listDataManager = ViewModelProviders.of(it).get(ListDataManager::class.java)
        }
        val taskLists = listDataManager.readLists()
        rvToDoList = view.findViewById(R.id.rv_tasks)
        rvToDoList.layoutManager = LinearLayoutManager(activity)
        rvToDoList.adapter = TaskListAdapter(taskLists,this)

        val fab = view.findViewById<FloatingActionButton>(R.id.btn_add_task_list)
        fab.setOnClickListener {
            showCreateToDoListDialog()
        }
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

    private fun addList(list: TaskList) {
        listDataManager.saveList(list)
        val toDoAdapter = rvToDoList.adapter as TaskListAdapter
        toDoAdapter.addList(list)
    }

    private fun showCreateToDoListDialog() {
        activity?.let {
            val dialogTitle = getString(R.string.dialog_title)
            val positiveBtnTitle = getString(R.string.dialog_btn_create)
            val myDialog = AlertDialog.Builder(it)
            val toDoTitleEditText = EditText(it)
            // set type of keyboard
            toDoTitleEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

            myDialog.setTitle(dialogTitle)
            myDialog.setView(toDoTitleEditText)

            myDialog.setPositiveButton(positiveBtnTitle) { dialog, _ ->
                val taskList = TaskList(toDoTitleEditText.text.toString())
                addList(taskList)
                dialog.dismiss()
                showTaskListItems(taskList)
            }
            myDialog.create().show()
        }
    }

    private fun showTaskListItems(taskList: TaskList) {
        view?.let {
            // pass data using the nav graph
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskListTasksFragment(taskList.name)
            it.findNavController().navigate(action)
        }
    }

}
