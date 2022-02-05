package com.example.expandingvocabulary.game

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
                .addOnFailureListener { Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show() }
        }
        translator.translate(binding.wordText.text.toString())
            .addOnSuccessListener {
                binding.translatedWord.apply {
                    visibility = View.VISIBLE
                    text = it
                }
                binding.translatedWord.clearAnimation()
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.translatedWord.apply {
                        if (binding.translatedWord.alpha == 1f) {
                            startAnimation(AnimationUtils.loadAnimation(context, R.anim.alpha_animation))
                            visibility = View.INVISIBLE
                        }
                    }
                }, 1000)
                binding.translatedWord.clearAnimation()
            }.addOnFailureListener {
                it.printStackTrace()
            }
    }

}