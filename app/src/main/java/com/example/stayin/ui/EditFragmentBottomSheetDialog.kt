package com.example.stayin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stayin.R
import com.example.stayin.databinding.FragmentEditBottemSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditFragmentBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditBottemSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditBottemSheetDialogBinding.inflate(layoutInflater)
        return binding.root
    }

}