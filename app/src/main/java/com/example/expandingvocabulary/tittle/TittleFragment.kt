package com.example.expandingvocabulary.tittle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expandingvocabulary.R
import com.example.expandingvocabulary.databinding.FragmentTittleBinding


class TittleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTittleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tittle, container, false)
        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TittleFragmentDirections.actionTittleFragmentToGameFragment())
        }
        return binding.root
    }

}