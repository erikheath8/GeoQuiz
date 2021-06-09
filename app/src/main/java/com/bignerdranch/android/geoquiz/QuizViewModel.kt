package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.geoquiz.MainActivity

private const val TAG = "QuizViewModel"

class QuizViewModel(var currentQuestionIndex: Int = 0) : ViewModel() {

    val currentIndex: Int
        get() =  currentQuestionIndex

    //val mainActivity = MainActivity.MainActivityCreate.create()
    //var currentIndex =  0
    //private  var correctAnswers = 0
    var numberAnswered = 0

    val questionBank = listOf<Question>(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, answer = true),
            Question(R.string.question_mideast, answer = false),
            Question(R.string.question_africa, answer = false),
            Question(R.string.question_americas, answer = true),
            Question(R.string.question_asia, answer = true))

    val currentQuestionAnswer : Boolean get() = questionBank[currentIndex].answer
    val currentQuestionText: Int get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentQuestionIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious(){
        currentQuestionIndex = (currentQuestionIndex - 1 + questionBank.size) %
                questionBank.size
    }

    // checks if question is answered and sets the Answered bool in the Question to true
    fun isAnswered(index: Int) {
        val isQuestionAnswered = questionBank[index].answered
        if (isQuestionAnswered) {

            //mainActivity.trueButton.isEnabled //= !isQuestionAnswered
            //mainActivity.falseButton.isEnabled //= !isQuestionAnswered
        }
    }

    /*
    val currentIndex: Int
        get() = currentQuestionIndex


    fun moveToNext() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentQuestionIndex = (currentQuestionIndex - 1 + questionBank.size) % questionBank.size
    }
    */
}