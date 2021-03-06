package com.example.expandingvocabulary.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val TIME = 60000L
    }

    private val timer: CountDownTimer

    private lateinit var wordList: MutableList<String>
    private val _score = MutableLiveData<Int>()
    private val _word = MutableLiveData<String>()
    private val _eventGameFinish = MutableLiveData<Boolean>()
    private val _currentTime = MutableLiveData<Long>()
    private val _translatedWord = MutableLiveData<String>()

    val score: LiveData<Int>
        get() = _score
    val word: LiveData<String>
        get() = _word
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish
    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeStringFormat = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    val translatedWord: LiveData<String>
        get() = _translatedWord

    init {
        updateWordsList()
        next()
        _score.value = 0
        _eventGameFinish.value = false
        _currentTime.value = 10L
        timer = object : CountDownTimer(TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }
        timer.start()
    }

    private fun updateWordsList() {
        wordList = mutableListOf(
            "extensions",
            "opportunity",
            "flexibility",
            "behavior",
            "achievable",
            "performance",
            "persuade",
            "harass",
            "exaggerate",
            "experience",
            "telltale",
            "onlooker",
            "breakthrough",
            "withdraw",
            "remuneration",
            "prioritising",
            "review",
            "daydream",
            "literally",
            "nonplussed",
            "disinterested",
            "unabashed",
            "accommodate",
            "deduction",
            "flabbergasted",
            "oblivious",
            "sustainable",
            "quaint",
            "ecstasy",
            "supersede",
            "superfluous",
            "maintenance",
            "liaison"
        )
        wordList.shuffle()
    }

    private fun next() {
        if (wordList.isEmpty()) {
            updateWordsList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onWrong() {
        next()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        next()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }


}