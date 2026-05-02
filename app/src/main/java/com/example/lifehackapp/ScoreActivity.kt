package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        // 1. GET the list of wrong answers from the QuizActivity
        val wrongAnswers = intent.getStringArrayListExtra("wrongAnswers")

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val reviewButton = findViewById<Button>(R.id.reviewButton)

        // --- NEW FIX: Updated grading logic for 20 questions ---
        val message = when (score) {
            20 -> "MasterHacker! Congratulations, you got everything right!🌟"
            in 15..19 -> "Keep up the good work, you almost got it!\uD83D\uDCAA"
            in 10..14 ->"Almost there, try harder next time!\uD83D\uDCDA"
            else -> "You'll get it next time!\uD83E\uDDE0"
        }
        // ---------------------------------------------------------------------------------------

        scoreText.text = "Score: $score/$total\n$message"

        reviewButton.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)

            // 2. PASS the list of wrong answers to the ReviewActivity
            intent.putStringArrayListExtra("wrongAnswers", wrongAnswers)

            startActivity(intent)
        }
    }
}