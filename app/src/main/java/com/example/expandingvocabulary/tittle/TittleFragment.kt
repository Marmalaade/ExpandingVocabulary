package com.example.expandingvocabulary.tittle

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expandingvocabulary.R
import com.example.expandingvocabulary.databinding.FragmentTittleBinding


open class TittleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTittleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tittle, container, false)
        binding.infoButton.setOnClickListener {
            showInfoDialog()
        }
        binding.playGameButton.setOnClickListener {
            findNavController().navigate(TittleFragmentDirections.actionTittleFragmentToGameFragment())
        }
        return binding.root
    }

    private fun showInfoDialog() {
        val dialog = context?.let { Dialog(it) }
        dialog?.setContentView(View.inflate(context, R.layout.custom_dialog, null))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setWindowAnimations(R.style.DialogAnimations)
        dialog?.show()
    }

}