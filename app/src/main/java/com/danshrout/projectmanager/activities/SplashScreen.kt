package com.danshrout.projectmanager.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.danshrout.projectmanager.R
import com.danshrout.projectmanager.firebase.FirestoreClass
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // This will hide the status bar on the top of the screen of the phone
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Set the font from the assets folder
        val typeface: Typeface = Typeface.createFromAsset(assets, "Magnificent.ttf")
        tv_splash_title.typeface = typeface

        // Here it checks to see if the user is signed in our not and decides what to do.
        Handler().postDelayed(
            {
                var currentUserID = FirestoreClass().getCurrentUserID()
                if (currentUserID.isNotEmpty()) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, IntroActivity::class.java))
                }
                finish()
            },
            3000
        )
    }
}
