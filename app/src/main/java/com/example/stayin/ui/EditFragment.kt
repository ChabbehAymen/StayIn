package com.example.stayin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.stayin.R
import com.example.stayin.databinding.FragmentEditBinding


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonClick(binding.navigateBackImageButton)
    }

    private fun onBackButtonClick(backButton: ImageButton){
        backButton.setOnClickListener {
            navigateToMainFragment()
        }
    }

    private fun navigateToMainFragment(){
        val action = R.id.action_editFragment_to_mainFragment
        findNavController().navigate(action)
    }

    private fun onShowDesignDialog(arrowUpButton: ImageButton){
        arrowUpButton.setOnClickListener {
            // show design dialog fragment
        }
    }

}