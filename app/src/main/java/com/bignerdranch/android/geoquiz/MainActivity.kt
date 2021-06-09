package com.bignerdranch.android.geoquiz

import android.icu.text.AlphabeticIndex
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.math.*
import com.bignerdranch.android.geoquiz.QuizViewModel

// logging
private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    //object MainActivityCreate {
      //  fun create(): MainActivity = create()
    //}

    private lateinit var quizViewModel: QuizViewModel
    lateinit var trueButton: Button
    lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var questionTextView: TextView

    /*private var quizViewModel: QuizViewModel by lazy {
        val factory = QuizViewModelFactory()
        ViewModelProvider(this@MainActivity, factory).get(QuizViewModel::class.java)
    }*/

    //private val quizViewModel: QuizViewModel by lazy
    //{ ViewModelProvider(this@QuizActivity).get(QuizViewModel::class.java) }
    //private val quizViewModel: QuizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // logging
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        val factory = QuizViewModelFactory(currentIndex)
        quizViewModel = ViewModelProvider(this@MainActivity, factory)
                .get(QuizViewModel::class.java)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            quizViewModel.isAnswered(quizViewModel.currentIndex)
            // below crashes program
            //scoredAnswers()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            quizViewModel.isAnswered(quizViewModel.currentIndex)
            // below crashes program
            //scoredAnswers()
        }

        previousButton.setOnClickListener {
            //if (currentIndex > 0) {
            //currentIndex = (currentIndex - 1)
            quizViewModel.moveToPrevious()
            updateQuestion()
            //} else {
            //currentIndex = questionBank.size - 1
            //updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()

        }
        updateQuestion()
    }

        // logging
        override fun onStart() {
            super.onStart()
            Log.d(TAG, "onStart() called")
        }

        // start of logging func()s
        override fun onResume() {
            super.onResume()
            Log.d(TAG, "onResume() called")
        }

        override fun onPause() {
            super.onPause()
            Log.d(TAG, "onPause() called")
        }

        override fun onStop() {
            super.onStop()
            Log.d(TAG, "onStop() called")
        }

        // end of logging func()s
        override fun onDestroy() {
            super.onDestroy()
            Log.d(TAG, "onStop() called")
        }

        override fun onSaveInstanceState(savedInstanceState: Bundle){
            super.onSaveInstanceState(savedInstanceState)
            Log.i(TAG, "onSaveInstanceState")
            savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        }

        private fun updateQuestion() {
            scoredAnswers()
            val questionTextResId = quizViewModel.currentQuestionText
            questionTextView.setText(questionTextResId)
            if (!quizViewModel.questionBank[quizViewModel.currentIndex].answered) {
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            }
            else {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }
        }

        // checks the correct answer for question and disables answered questions
        private fun checkAnswer(userAnswer: Boolean) {
            val correctAnswer = quizViewModel.currentQuestionAnswer
            val messageResId = if (userAnswer == correctAnswer) {
                R.string.correct_toast
            } else {
                R.string.incorrect_toast
            }
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            quizViewModel.isAnswered(quizViewModel.currentIndex)
            quizViewModel.questionBank[quizViewModel.currentIndex].answered = true
        }

        private fun scoredAnswers() {
               if (quizViewModel.questionBank[quizViewModel.currentIndex].answered)
                   quizViewModel.numberAnswered ++
                   if (quizViewModel.numberAnswered == quizViewModel.questionBank.size) {
                       val questionsEnd = R.string.questions_end
                        Toast.makeText(this, questionsEnd, Toast.LENGTH_SHORT).show()

                //val percentCorrect : Int = abs(10 * (correctAnswers / questionBank.size))
                //Toast.makeText(this, percentCorrect, Toast.LENGTH_SHORT).show()
            }


        }

    }
