package com.danshrout.projectmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupActionBar()
    }

    // Action bar used for navigating back to the Intro Activity.
    private fun setupActionBar() {
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            // This will make a back arrow to navigate back.
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
    }
}