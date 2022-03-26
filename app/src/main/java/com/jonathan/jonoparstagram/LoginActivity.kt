package com.jonathan.jonoparstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Check if there is a logged in user
        //If so, take user to MainActivity
        if(ParseUser.getCurrentUser()!=null){
            goToMainActivity()
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username,password)
        }

        findViewById<Button>(R.id.btnSignUp).setOnClickListener{
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
           signUpUser(username,password)
        }
    }

    private fun signUpUser(username: String,password: String){
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
                user.setUsername(username)
                user.setPassword(password)

                user.signUpInBackground { e ->
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        //1. Navigate user to MainActivity
                        Log.i(TAG,"Successfully Signed Up user")
                        Toast.makeText(this,"Signed up Successfully!",Toast.LENGTH_SHORT).show()
                        goToMainActivity()
                        //2. Show a toast to indicate that user signed up sucessfully
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        e.printStackTrace()
                        Toast.makeText(this, "Error signing up", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun loginUser(username: String, password: String){
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                // Hooray!  The user is logged in.
                Log.i(TAG,"Successfully logged in user")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
                // Signup failed.  Look at the ParseException to see what happened.
            }})
        )

    }

    private fun goToMainActivity(){
        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
       const val TAG = "LoginActivity"
    }
}