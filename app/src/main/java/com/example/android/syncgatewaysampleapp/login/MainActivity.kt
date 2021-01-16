package com.example.android.syncgatewaysampleapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.android.syncgatewaysampleapp.util.DatabaseManager
import com.example.android.syncgatewaysampleapp.R
import com.example.android.syncgatewaysampleapp.profile.UserProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById<EditText>(R.id.etUsername)
        etPassword = findViewById<EditText>(R.id.etPassword)
    }

    fun onLoginTapped(view: View) {
        if (etUsername.length() > 0 && etPassword.length() > 0) {

            var username = etUsername.text.toString()
            var password = etPassword.text.toString()

            val context = applicationContext

            var dbManager: DatabaseManager = DatabaseManager.getSharedInstance()

            dbManager.initializeCouchbaseLite(context)
            dbManager.openPrebuiltDatabase(context)
            dbManager.openOrCreateDatabase(context, username)

            DatabaseManager.startPushAndPullReplicationForCurrentUser(username, password)

            var intent: Intent = Intent(applicationContext, UserProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

        }
    }
}