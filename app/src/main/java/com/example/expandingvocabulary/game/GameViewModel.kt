package com.example.expandingvocabulary.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private lateinit var wordList: MutableList<String>
    var score = 0
    var word = ""

    init {
        updateWordsList()
        next()
    }

    private fun updateWordsList() {
        wordList = mutableListOf(
            "extensions",
            "opportunity",
            "flexibility",
            "behavior"
        )
        wordList.shuffle()
    }

    private fun next() {
        if (wordList.isEmpty()) {
            // gameFinished()
        } else {
            word = wordList.removeAt(0)
        }
    }

    fun onWrong() {
        if (score > 0) {
            score--
        }
        next()
    }

    fun onCorrect() {
        score++
        next()
    }
}