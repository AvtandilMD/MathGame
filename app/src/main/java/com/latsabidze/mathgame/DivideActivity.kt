package com.latsabidze.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.random.Random

class DivideActivity : AppCompatActivity() {
    lateinit var textScore: TextView
    lateinit var textLife: TextView
    lateinit var textTime: TextView
    lateinit var textQuestion: TextView
    lateinit var editTextAnswer: EditText
    lateinit var buttonOk: Button
    lateinit var buttonNext: Button
    var correctAnswer = 0
    var userScore = 0
    var userLife = 3
    private var timer: CountDownTimer? = null
    private val startTimerInMillis: Long = 60000
    var timeLeftInMillis: Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeViews()
        gameContinue()
        setupClickListeners()
    }

    private fun initializeViews() {
        textScore = findViewById(R.id.textViewScore)
        textLife = findViewById(R.id.textViewLife)
        textTime = findViewById(R.id.textViewTime)
        textQuestion = findViewById(R.id.textViewQuestion)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonOk = findViewById(R.id.buttonOk)
        buttonNext = findViewById(R.id.buttonNext)
    }

    private fun setupClickListeners() {
        buttonOk.setOnClickListener {
            val input = editTextAnswer.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(
                    applicationContext, "გთხოვ დაწერე პასუხი, ან დააჭირე ღილაკს <შემდეგი>",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                try {
                    pauseTimer()
                    val userAnswer = input.toInt()
                    checkAnswer(userAnswer)
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        applicationContext, "გთხოვ შეიყვანე მხოლოდ რიცხვი",
                        Toast.LENGTH_LONG
                    ).show()
                    startTimer() // Resume timer if input was invalid
                }
            }
        }

        buttonNext.setOnClickListener {
            resetTimer()
            pauseTimer()
            editTextAnswer.setText("")
            if (userLife == 0) {
                endGame()
            } else {
                gameContinue()
            }
        }
    }

    private fun checkAnswer(userAnswer: Int) {
        if (userAnswer == correctAnswer) {
            userScore += 10
            textQuestion.text = "გილოცავ!\nშენი პასუხი სწორია"
            textScore.text = userScore.toString()
        } else {
            userLife--
            textQuestion.text = "ვწუხვარ!\nშენი პასუხი არასწორია\nსწორი პასუხი: $correctAnswer"
            textLife.text = userLife.toString()
        }
    }

    private fun endGame() {
        Toast.makeText(applicationContext, "თამაში დასრულდა", Toast.LENGTH_LONG).show()
        val intent = Intent(this@DivideActivity, ResultActivity::class.java)
        intent.putExtra("score", userScore)
        startActivity(intent)
        finish()
    }

    fun gameContinue() {
        // Generate problems with even numbers only
        val divisor = generateEvenNumber(2, 10) // ლუწი რიცხვი 2-დან 10-მდე
        val quotient = Random.nextInt(2, 10)
        val dividend = divisor * quotient

        textQuestion.text = "$dividend : $divisor"
        correctAnswer = quotient
        startTimer()
    }

    private fun generateEvenNumber(min: Int, max: Int): Int {
        var number: Int
        do {
            number = Random.nextInt(min, max + 1)
        } while (number % 2 != 0) // განმეორდება სანამ ლუწ რიცხვს არ მივიღებთ
        return number
    }

    fun startTimer() {
        timer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()
                userLife--
                textLife.text = userLife.toString()
                textQuestion.text = "ვწუხვარ! დრო ამოიწურა\nსწორი პასუხი: $correctAnswer"
            }
        }.start()
    }

    fun updateText() {
        val remainingTime: Int = (timeLeftInMillis / 1000).toInt()
        textTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    fun pauseTimer() {
        timer!!.cancel()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        pauseTimer() // Clean up timer to prevent memory leaks
//    }
//
//    override fun onPause() {
//        super.onPause()
//        pauseTimer() // Pause timer when activity is not visible
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (userLife > 0) {
//            startTimer() // Resume timer when activity becomes visible again
//        }
//    }
}
