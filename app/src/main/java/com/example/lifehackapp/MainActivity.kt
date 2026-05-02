/** Second Introduction to Mobile Application Development Assignment
 * Student Number: ST10538583
 *
 * References:
 *  Android Developers. (2024). *Guide to app architecture*. Retrieved from https://developer.android.com/topic/architecture
 * Jetbrains. (2024). *Arrays in Kotlin*. Retrieved from https://kotlinlang.org/docs/arrays.html#when-to-use-arrays
 * Android Developers. (2024). *Intents and Intent Filters*. Retrieved from https://developer.android.com/guide/components/intents-filters
 * Android Developers. (2024). *Handle back navigation*. Retrieved from https://developer.android.com/guide/navigation/custom-back
 * Chat Gemini AI Android Studio Kotlin. *Fix any errors*
 */


package com.example.lifehackapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // 2. LOG: Prove the app started successfully
        Log.d("MainActivityLifecycle", "onCreate: App started successfully")

        // Handle Window Insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup Start Button navigation
        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            // 3. LOG: Prove you understand the button was clicked
            Log.d("ButtonAction", "Start Button clicked. Preparing to launch QuizActivity")

            // This takes the user to the QuizActivity
            startActivity(Intent(this, QuizActivity::class.java))
        }

        // --- "DO YOU WISH TO LEAVE?" POPUP ---
        onBackPressedDispatcher.addCallback(this) {
            // 4. LOG: Prove the back button was pressed
            Log.d("Navigation", "User pressed back. Showing 'Leave Game?' dialog.")

            AlertDialog.Builder(this@MainActivity)
                .setTitle("Leave Game?")
                .setMessage("Do you wish to leave the game?")
                .setPositiveButton("Yes") { _, _ ->
                    // 5. LOG: Prove what happens when they click Yes
                    finishAffinity() // Completely closes the app
                }
                .setNegativeButton("No") { dialog, _ ->
                    // 6. LOG: Prove what happens when they click No
                    dialog.dismiss() // Closes the popup, stays on app
                }
                .show()
        }
    }
}