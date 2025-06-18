package com.example.bukuapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bukuapp.database.DatabaseHelper
import com.example.bukuapp.model.User
import com.google.android.material.textfield.TextInputEditText

class EditProfileActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var nameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var phoneInput: TextInputEditText
    private var currentUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        dbHelper = DatabaseHelper(this)
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        phoneInput = findViewById(R.id.phoneInput)

        // Get current user from SharedPreferences
        val username = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            .getString("username", null)

        if (username != null) {
            currentUser = dbHelper.getUserProfile(username)
            currentUser?.let { user ->
                nameInput.setText(user.name)
                emailInput.setText(user.email)
                phoneInput.setText(user.phone)
            }
        }

        findViewById<android.widget.Button>(R.id.saveButton).setOnClickListener {
            val name = nameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (username != null) {
                val result = dbHelper.updateUserProfile(username, name, email, phone)
                if (result > 0) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
} 