package com.example.expandingvocabulary.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private lateinit var wordList: MutableList<String>
    private val _score = MutableLiveData<Int>()
    private val _word = MutableLiveData<String>()
    private val _eventGameFinish = MutableLiveData<Boolean>()

    val score: LiveData<Int>
        get() = _score
    val word: LiveData<String>
        get() = _word
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        updateWordsList()
        next()
        _score.value = 0
        _word.value = ""
        _eventGameFinish.value = false
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
            _eventGameFinish.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onWrong() {
        if (score.value!! > 0) {
            _score.value = (score.value)?.minus(1)
        }
        next()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        next()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}