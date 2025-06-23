package com.example.bukuapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bukuapp.model.Book


        recyclerView = findViewById(R.id.booksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookAdapter(Book.dummyBooks)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.action_logout -> {
                // Clear user session
                getSharedPreferences("UserPrefs", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()
                
                // Return to login screen
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 