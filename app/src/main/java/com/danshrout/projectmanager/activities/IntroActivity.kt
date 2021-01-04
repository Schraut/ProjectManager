package com.danshrout.projectmanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.danshrout.projectmanager.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // This will hide the status bar on the top of the screen of the phone
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Button to navigate to the sign up screen
        btn_sign_up_screen.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Button to navigate to the sign in screen
        btn_sign_in_screen.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}
