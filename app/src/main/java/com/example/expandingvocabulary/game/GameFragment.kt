package com.example.expandingvocabulary.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expandingvocabulary.R
import com.example.expandingvocabulary.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var wordList: MutableList<String>

    private var score = 0
    private var word = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        updateWordsList()
        next()
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onWrong() }
        updateBinding()

        return binding.root
    }

    private fun onWrong() {
        if (score > 0) {
            score--
        }
        next()
    }

    private fun next() {
        if (wordList.isEmpty()) {
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToScoreFragment(score))
        } else {
            word = wordList.removeAt(0)
        }
        updateBinding()
    }

    private fun onCorrect() {
        score++
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

    private fun updateBinding() {
        binding.scoreText.text = score.toString()
        binding.wordText.text = word
    }

}