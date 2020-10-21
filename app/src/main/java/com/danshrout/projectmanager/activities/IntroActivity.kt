package com.danshrout.projectmanager.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danshrout.projectmanager.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

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