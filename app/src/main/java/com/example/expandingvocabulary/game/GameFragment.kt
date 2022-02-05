package com.example.expandingvocabulary.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.translation.Translator
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.expandingvocabulary.R
import com.example.expandingvocabulary.databinding.FragmentGameBinding
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions


class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var translationConfigs: TranslatorOptions
    private lateinit var translator: com.google.mlkit.nl.translate.Translator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        binding.translateButton.setOnClickListener {
            translateWords()
        }

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer
        { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })

        return binding.root
    }

    private fun gameFinished() {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value ?: 0))
    }

    private fun translateWords() {
        translationConfigs = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.RUSSIAN)
            .build()

        translator = Translation.getClient(translationConfigs)

        if (binding.wordText.text.isNotEmpty()) {
            translator.downloadModelIfNeeded()
                .addOnSuccessListener { Toast.makeText(context, "Download Successful", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show() }
        }
        translator.translate(binding.wordText.text.toString())
            .addOnSuccessListener {
                binding.translatedWord.visibility = View.VISIBLE
                binding.translatedWord.text = it
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

}