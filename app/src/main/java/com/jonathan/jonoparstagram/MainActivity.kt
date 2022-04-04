package com.jonathan.jonoparstagram

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jonathan.jonoparstagram.fragments.ComposeFragment
import com.jonathan.jonoparstagram.fragments.FeedFragment
import com.jonathan.jonoparstagram.fragments.ProfileFragment
import com.parse.*
import java.io.File

/**
 * Let user create a post by taking a photo with their camera
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener{

            item ->

            var fragmentToShow: Fragment? = null
            when(item.itemId){

                R.id.action_home -> {
                    fragmentToShow = FeedFragment()

                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }

            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            //Returning true says that we've handled the user interaction on the item
            true
        }

        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

       /* findViewById<Button>(R.id.btnLogout).setOnClickListener{
            ParseUser.logOut()
            val currentUser = ParseUser.getCurrentUser() // this will now be null
            Log.i(TAG,"User is now logged out")
            Toast.makeText(this, "You have successfully logged out!",Toast.LENGTH_SHORT).show()
            goToLogin()
        }

      queryPosts() */
    }

   /* private fun goToLogin(){
        val intent = Intent(this@MainActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    } */



    companion object{
        const val TAG = "MainActivity"
    }
}
