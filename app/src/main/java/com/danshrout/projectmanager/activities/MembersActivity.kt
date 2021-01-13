package com.danshrout.projectmanager.activities

import android.os.Bundle
import com.danshrout.projectmanager.R
import com.danshrout.projectmanager.models.Board
import com.danshrout.projectmanager.utils.Constants
import kotlinx.android.synthetic.main.activity_members.*

class MembersActivity : BaseActivity() {
    // Variable for Board Details.
    private lateinit var mBoardDetails: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)

        // Gets the board details through intent and assigns it.
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra<Board>(Constants.BOARD_DETAIL)!!
        }

        setupActionBar()
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
}
