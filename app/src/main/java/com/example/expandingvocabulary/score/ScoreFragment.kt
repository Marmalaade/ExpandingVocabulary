package com.example.expandingvocabulary.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expandingvocabulary.R
import com.example.expandingvocabulary.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentScoreBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        binding.scoreText.text = scoreFragmentArgs.score.toString()
        binding.backGameButton.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToTittleFragment())
        }
        return binding.root
    }
}