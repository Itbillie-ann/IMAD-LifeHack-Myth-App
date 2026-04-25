package com.example.lifehackapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    var index = 0
    var score = 0
    lateinit var questionText: TextView
    lateinit var feedbackText: TextView

    data class QuizItem(val question: String, val isHack: Boolean, val explanation: String)

    private val masterQuestions = listOf(
        QuizItem("Humans have exactly five senses", false, "Myth: You have at least 9 senses (balance, temperature, pain, proprioception, etc.). 'Five senses' is ancient philosophy, not biology."),
        QuizItem("Using a password manager improves your security", true, "Hack: Password managers generate unique strong passwords and reduce reuse — the #1 cause of account breaches."),
        QuizItem("Shaving makes hair grow back thicker and darker", false, "Myth: Shaving cuts hair at the blunt end, making it feel coarse — but thickness, color, and growth rate don't change."),
        QuizItem("The 'two-minute rule' helps beat procrastination", true, "Hack: If a task takes <2 minutes, do it immediately. It clears small tasks and builds momentum."),
        QuizItem("Sugar makes children hyperactive", false, "Myth: Over 12 double-blind studies found no link. Sugar doesn't cause hyperactivity — expectation does."),
        QuizItem("Putting a spoon in an open champagne bottle keeps it fizzy longer", true, "Hack: The cold spoon keeps the neck's air colder than the bottle, slowing CO₂ escape. Works best with a tight seal."),
        QuizItem("You lose most of your body heat through your head", false, "Myth: Heat loss depends on exposed surface area. A bare arm loses just as much as a bare head."),
        QuizItem("Splitting your screen reduces multitasking errors", true, "Hack: Alt+Tab (or Split View) reduces context switching and keeps reference material visible."),
        QuizItem("Eating turkey makes you sleepy because of tryptophan", false, "Myth: Turkey has no more tryptophan than chicken or beef. Carb-heavy feasts cause sleepiness, not turkey."),
        QuizItem("The Pomodoro Technique (25 min work, 5 min break) boosts focus", true, "Hack: Short sprints with breaks prevent burnout and maintain high focus, proven in productivity studies."),
        QuizItem("Vitamin C prevents the common cold", false, "Myth: Vitamin C doesn't prevent colds. It may shorten duration by 8% in some people, that's all."),
        QuizItem("Taking notes by hand improves memory better than typing", true, "Hack: Handwriting forces deeper processing and paraphrasing, while typing often becomes mindless transcription."),
        QuizItem("You should drink 8 glasses of water a day", false, "Myth: No scientific backing. Drink when thirsty — needs vary by person, activity, and climate."),
        QuizItem("Decluttering your workspace reduces cognitive load", true, "Hack: Visual clutter competes for attention. A clean space lowers distraction and decision fatigue."),
        QuizItem("We evolved from chimpanzees", false, "Myth: We share a common ancestor with chimps from 6-8 million years ago, but we didn't evolve from them."),
        QuizItem("Listening to instrumental music while coding improves concentration", true, "Hack: Lyrics compete with language-processing brain areas. Instrumental music fills the 'auditory sweet spot'."),
        QuizItem("Lightning never strikes the same place twice", false, "Myth: Lightning often strikes the same place repeatedly — Empire State Building gets hit ~25 times per year."),
        QuizItem("Turning on 'night mode' before bed reduces blue light interference with sleep", true, "Hack: Blue light suppresses melatonin. Night mode shifts to warmer colors, helping your circadian rhythm."),
        QuizItem("The Great Wall of China is the only man-made structure visible from space", false, "Myth: Many things are visible from space (highways, dams, airports). The Great Wall is hard to spot even from low orbit."),
        QuizItem("Eating protein within 30 minutes after a workout aids muscle recovery", true, "Hack: The 'anabolic window' is real — early protein intake kickstarts muscle protein synthesis after resistance training.")
    )

    private lateinit var currentQuiz: List<QuizItem>

    // 1. ADD THIS: A list to hold EVERY wrong answer
    private val wrongAnswersList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionText = findViewById(R.id.questionText)
        feedbackText = findViewById(R.id.feedbackText)

        val hackButton = findViewById<Button>(R.id.hackButton)
        val mythButton = findViewById<Button>(R.id.mythButton)
        val nextButton = findViewById<Button>(R.id.nextButton)

        currentQuiz = masterQuestions.shuffled()

        loadQuestion()

        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }

        nextButton.setOnClickListener {
            index++

            if (index < currentQuiz.size) {
                loadQuestion()
                feedbackText.text = ""
            } else {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("total", currentQuiz.size)

                // 2. ADD THIS: Put the whole list of wrong answers into the intent
                intent.putStringArrayListExtra("wrongAnswers", ArrayList(wrongAnswersList))

                startActivity(intent)
                finish()
            }
        }
    }

    fun loadQuestion() {
        questionText.text = currentQuiz[index].question
    }

    fun checkAnswer(userAnswer: Boolean) {
        val currentItem = currentQuiz[index]

        if (userAnswer == currentItem.isHack) {
            feedbackText.text = "Correct! 🎉\n${currentItem.explanation}"
            score++
        } else {
            feedbackText.text = "Wrong! ❌\n${currentItem.explanation}"

            // 3. ADD THIS: When wrong, format the text and add it to our list!
            val correctAnswerText = if (currentItem.isHack) "Hack ✅" else "Myth ❌"
            val wrongItemText = "Question: ${currentItem.question}\nCorrect Answer: $correctAnswerText\n${currentItem.explanation}\n\n---\n\n"
            wrongAnswersList.add(wrongItemText)
        }
    }
}