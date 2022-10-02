package com.example.stayin.presentation.ui.editFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.stayin.R
import com.example.stayin.databinding.FragmentEditBinding
import com.example.stayin.presentation.ui.SharedViewModel
import com.example.stayin.presentation.utils.ConstantValues
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    private var toEditNoteId = ConstantValues.NULL_ARGUMENT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            toEditNoteId = it.getInt("noteId")
        }
        isOnEditMode()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEditModeUiUpdate()
        setDateAndTimeText(binding.dateAndTimeText)
        onBackButtonClick(binding.navigateBackImageButton)
        onShowEditFragmentBottomSheetDialog(binding.bottomSheetDialogBtn)
        onSaveButtonClicked(binding.saveNoteImageButton)
        observeTextInputChange()
    }

    private fun isOnEditMode() {
        if (toEditNoteId != ConstantValues.NULL_ARGUMENT) {
            viewModel.getIsOnEditMode(true)
            viewModel.getNoteById(toEditNoteId)
        }
    }

    private fun onEditModeUiUpdate() {
        if (viewModel.isOnEditMode){
            val noteItem = viewModel.editingNote
            binding.apply {
                textFieldForTitle.setText(noteItem.title)
                textFieldForNote.setText(noteItem.text)
            }
        }
    }

    private fun onBackButtonClick(backButton: ImageButton) {
        backButton.setOnClickListener {
            navigateToMainFragment()
        }
    }

    private fun navigateToMainFragment() {
        val action = R.id.action_editFragment_to_mainFragment
        findNavController().navigate(action)
    }

    private fun onShowEditFragmentBottomSheetDialog(arrowUpButton: ImageButton) {
        arrowUpButton.setOnClickListener {
            showEditFragmentBottomSheetDialog()
        }
    }

    private fun showEditFragmentBottomSheetDialog() {
        val bottomSheetDialog = EditFragmentBottomSheetDialog()
        bottomSheetDialog.show(childFragmentManager.beginTransaction(), "Bottom Sheet")
    }

    private fun getCurrentDateAndTime(): String {
        val simpleDateFormat = SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

    private fun setDateAndTimeText(itTextView: TextView) {
        viewModel.getNoteDateAndTime(getCurrentDateAndTime())
        itTextView.text = getCurrentDateAndTime()
    }

    private fun onSaveButtonClicked(saveImageButton: ImageButton) {
        saveImageButton.setOnClickListener {
            if (isNoteValid())
                makeToastError()
            else {
                viewModel.insertNewNote()
                navigateToMainFragment()

            }
        }
    }

    private fun isNoteValid(): Boolean {
        return isNoteTextFieldEmpty() && isTitleTextFieldEmpty()
    }

    private fun isTitleTextFieldEmpty(): Boolean {
        val titleTextField = binding.textFieldForTitle
        return titleTextField.text!!.isEmpty()
    }

    private fun isNoteTextFieldEmpty(): Boolean {
        val noteTextField = binding.textFieldForTitle
        return noteTextField.text!!.isEmpty()
    }

    private fun makeToastError() {
        Toast.makeText(requireContext(), "Title or Text should note be empty", Toast.LENGTH_SHORT)
            .show()
    }

    private fun observeTextInputChange() {
        observeTitleTextChange()
        observeNoteTextChange()
    }

    private fun observeTitleTextChange() {
        binding.textFieldForTitle.doOnTextChanged { text, _, _, _ ->
            viewModel.getNoteTitle(text.toString())
        }
    }

    private fun observeNoteTextChange() {
        binding.textFieldForNote.doOnTextChanged { text, _, _, _ ->
            viewModel.getNoteText(text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}