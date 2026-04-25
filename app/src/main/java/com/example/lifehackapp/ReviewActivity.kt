package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_review)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layout = findViewById<LinearLayout>(R.id.reviewLayout)
        val wrongAnswers = intent.getStringArrayListExtra("wrongAnswers")

        if (wrongAnswers != null && wrongAnswers.isNotEmpty()) {
            for (wrongItem in wrongAnswers) {
                val tv = TextView(this)
                tv.text = wrongItem
                tv.textSize = 18f
                tv.setPadding(0, 0, 0, 48)
                layout.addView(tv)
            }
        } else {
            val tv = TextView(this)
            tv.text = "Perfect Score! You didn't get any questions wrong. 🌟"
            tv.textSize = 22f
            layout.addView(tv)
        }

        // --- NEW CODE FOR BUTTONS STARTS HERE ---
        val homeButton = findViewById<Button>(R.id.homeButton)
        val restartButton = findViewById<Button>(R.id.restartButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            // These flags make sure the back button doesn't take them back to the finished quiz
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        restartButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            // These flags make sure the back button doesn't take them back to the finished quiz
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        // --- NEW CODE FOR BUTTONS ENDS HERE ---
    }
}