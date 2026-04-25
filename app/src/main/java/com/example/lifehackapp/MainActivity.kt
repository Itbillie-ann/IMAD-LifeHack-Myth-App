package com.example.lifehackapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle Window Insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Start Button navigation
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            // This takes the user to the QuizActivity
            startActivity(Intent(this, QuizActivity::class.java))
        }

        // --- "DO YOU WISH TO LEAVE?" POPUP ---
        onBackPressedDispatcher.addCallback(this) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Leave Game?")
                .setMessage("Do you wish to leave the game?")
                .setPositiveButton("Yes") { _, _ ->
                    finishAffinity() // Completely closes the app
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss() // Closes the popup, stays on app
                }
                .show()
        }
    }
}