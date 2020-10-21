package com.danshrout.projectmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.danshrout.projectmanager.R
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Create action bar
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

        btn_sign_up.setOnClickListener {
            registerUser()
        }
    }


    private fun registerUser() {
        // Here we get the text from editText and trim the space
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

//        if (validateForm(name, email, password)) {
//            // Show the progress dialog.
//            showProgressDialog(resources.getString(R.string.please_wait))
//            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(
//                    OnCompleteListener<AuthResult> { task ->
//
//                        // If the registration is successfully done
//                        if (task.isSuccessful) {
//
//                            // Firebase registered user
//                            val firebaseUser: FirebaseUser = task.result!!.user!!
//                            // Registered Email
//                            val registeredEmail = firebaseUser.email!!
//
//                            val user = User(
//                                firebaseUser.uid, name, registeredEmail
//                            )
//
//                            // call the registerUser function of FirestoreClass to make an entry in the database.
//                            FirestoreClass().registerUser(this@SignUpActivity, user)
//                        } else {
//                            Toast.makeText(
//                                this@SignUpActivity,
//                                task.exception!!.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    })
//        }
    }

    // Validation form
    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }
}