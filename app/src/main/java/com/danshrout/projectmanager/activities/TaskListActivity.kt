package com.danshrout.projectmanager.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.danshrout.projectmanager.R
import com.danshrout.projectmanager.adapters.TaskListItemsAdapter
import com.danshrout.projectmanager.firebase.FirestoreClass
import com.danshrout.projectmanager.models.Board
import com.danshrout.projectmanager.models.Card
import com.danshrout.projectmanager.models.Task
import com.danshrout.projectmanager.utils.Constants
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : BaseActivity() {

    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        var boardDocumentId = ""
        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)
        }

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this, boardDocumentId)
    }

    // Inflates the action menu for TaskListScreen and also launches the MembersActivity Screen on item selection
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.menu_members, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handle presses on the action bar menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_members -> {
                // Passes the board details through intent
                val intent = Intent(this@TaskListActivity, MembersActivity::class.java)
                intent.putExtra(Constants.BOARD_DETAIL, mBoardDetails)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_task_list_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = mBoardDetails.name
        }

        toolbar_task_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun boardDetails(board: Board) {
        mBoardDetails = board
        hideProgressDialog()
        setupActionBar()
        // Here we are appending an item view for adding a list task list for the board.
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        rv_task_list.layoutManager =
            LinearLayoutManager(this@TaskListActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)

        // Create an instance of TaskListItemsAdapter and pass the task list to it.
        val adapter = TaskListItemsAdapter(this@TaskListActivity, board.taskList)
        rv_task_list.adapter = adapter // Attach the adapter to the recyclerView.
    }

    // Get the result of adding or updating the task list.
    fun addUpdateTaskListSuccess() {
        hideProgressDialog()
        // Here get the updated board details.
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this@TaskListActivity, mBoardDetails.documentId)
    }

    // Get the task list name from the adapter class to create a new task list in the database.
    fun createTaskList(taskListName: String) {
        Log.e("Task List Name", taskListName)
        // Create and Assign the task details
        val task = Task(taskListName, FirestoreClass().getCurrentUserID())

        mBoardDetails.taskList.add(0, task) // Add task to the first position of ArrayList
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1) // Remove the last position as we have added the item manually for adding the TaskList.

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    // Update the taskList
    fun updateTaskList(position: Int, listName: String, model: Task) {

        val task = Task(listName, model.createdBy)

        mBoardDetails.taskList[position] = task
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1)

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    // Delete a task list from database.
    fun deleteTaskList(position: Int) {

        mBoardDetails.taskList.removeAt(position)
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1)

        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }

    // Create a card and update it in the task list.
    fun addCardToTaskList(position: Int, cardName: String) {
        // Remove the last item
        mBoardDetails.taskList.removeAt(mBoardDetails.taskList.size - 1)

        val cardAssignedUsersList: ArrayList<String> = ArrayList()
        cardAssignedUsersList.add(FirestoreClass().getCurrentUserID())

        val card = Card(cardName, FirestoreClass().getCurrentUserID(), cardAssignedUsersList)

        val cardsList = mBoardDetails.taskList[position].cards
        cardsList.add(card)

        val task = Task(
            mBoardDetails.taskList[position].title,
            mBoardDetails.taskList[position].createdBy,
            cardsList
        )

        mBoardDetails.taskList[position] = task
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addUpdateTaskList(this@TaskListActivity, mBoardDetails)
    }
}
