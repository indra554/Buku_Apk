package com.example.bukuapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bukuapp.database.DatabaseHelper
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)

        // Get username from SharedPreferences
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = prefs.getString("username", "") ?: ""

        // Load user data from database
        val user = dbHelper.getUserProfile(username)
        if (user != null) {
            // Set user data to views
            findViewById<TextView>(R.id.nameText).text = user.name
            findViewById<TextView>(R.id.emailText).text = user.email
            findViewById<TextView>(R.id.phoneText).text = user.phone
        } else {
            Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show()
        }

        // Set up edit profile button
        findViewById<MaterialButton>(R.id.editProfileButton).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        // Refresh profile data when returning from EditProfileActivity
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = prefs.getString("username", "") ?: ""
        val user = dbHelper.getUserProfile(username)
        if (user != null) {
            findViewById<TextView>(R.id.nameText).text = user.name
            findViewById<TextView>(R.id.emailText).text = user.email
            findViewById<TextView>(R.id.phoneText).text = user.phone
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 