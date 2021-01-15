package com.danshrout.projectmanager.activities

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.danshrout.projectmanager.R
import com.danshrout.projectmanager.adapters.MemberListItemsAdapter
import com.danshrout.projectmanager.firebase.FirestoreClass
import com.danshrout.projectmanager.models.Board
import com.danshrout.projectmanager.models.User
import com.danshrout.projectmanager.utils.Constants
import kotlinx.android.synthetic.main.activity_members.*
import kotlinx.android.synthetic.main.dialog_search_member.*

class MembersActivity : BaseActivity() {
    // Variable for Board Details.
    private lateinit var mBoardDetails: Board

    private lateinit var mAssignedMembersList: ArrayList<User>
    // A global variable for notifying any changes in the assigned members list.
    private var anyChangesMade: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        // Gets the board details through intent and assigns it.
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)!!
        }

        setupActionBar()

        showProgressDialog(resources.getString(R.string.please_wait))
        // Get the members list details from the database
        FirestoreClass().getAssignedMembersListDetails(
            this@MembersActivity,
            mBoardDetails.assignedTo
        )
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_members_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_members_activity.setNavigationOnClickListener { onBackPressed() }
    }
    // Setup assigned members list into recyclerview
    fun setupMembersList(list: ArrayList<User>) {

        mAssignedMembersList = list

        hideProgressDialog()

        rv_members_list.layoutManager = LinearLayoutManager(this@MembersActivity)
        rv_members_list.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this@MembersActivity, list)
        rv_members_list.adapter = adapter
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_add_member -> {
                // The dialogSearchMember function is called here
                dialogSearchMember()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    // Show the Custom Dialog.
    private fun dialogSearchMember() {
        val dialog = Dialog(this)
        // Set the screen content from a layout resource.
        // The resource will be inflated, adding all top-level views to the screen.
        dialog.setContentView(R.layout.dialog_search_member)
        dialog.tv_add.setOnClickListener(
            View.OnClickListener {

                val email = dialog.et_email_search_member.text.toString()

                if (email.isNotEmpty()) {
                    dialog.dismiss()
                    showProgressDialog(resources.getString(R.string.please_wait))
                    // Get the member details from the database
                    FirestoreClass().getMemberDetails(this@MembersActivity, email)
                } else {
                    Toast.makeText(
                        this@MembersActivity,
                        "Please enter members email address.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        dialog.tv_cancel.setOnClickListener(
            View.OnClickListener {
                dialog.dismiss()
            }
        )
        // Start the dialog and display it on screen.
        dialog.show()
    }

    // Get the result of the member if it's in the database.)
    fun memberDetails(user: User) {
        // Here add the user id to the existing assigned members list of the board
        mBoardDetails.assignedTo.add(user.id)
        // Assign the member to the board.)
        FirestoreClass().assignMemberToBoard(this@MembersActivity, mBoardDetails, user)
    }

    // Initialize the dialog for searching member from Database. Get the result of assigning the members.
    fun memberAssignSuccess(user: User) {
        hideProgressDialog()
        mAssignedMembersList.add(user)
        anyChangesMade = true
        setupMembersList(mAssignedMembersList)
    }

    // Send the result to the base activity onBackPressed.
    override fun onBackPressed() {
        if (anyChangesMade) {
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()
    }
}
