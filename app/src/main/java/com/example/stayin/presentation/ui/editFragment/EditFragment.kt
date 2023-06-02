package com.example.stayin.presentation.ui.editFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.stayin.R
import com.example.stayin.data.NoteDatabase
import com.example.stayin.databinding.FragmentEditBinding
import com.example.stayin.presentation.ui.editFragment.model.SharedViewModel
import com.example.stayin.presentation.ui.editFragment.model.SharedViewModelFactory
import com.example.stayin.presentation.utils.ConstantValues
import com.example.stayin.repository.RepoImplementation
import com.example.stayin.useCases.*
import java.text.SimpleDateFormat
import java.util.*


class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    // I didn't know how to use Di, That's why I am using this horrible way /n
    private val viewModel by activityViewModels<SharedViewModel> {
        val repo = RepoImplementation(NoteDatabase.getDatabase(requireContext()).noteDao())
        SharedViewModelFactory(
            NoteUseCase(
                GetNotesUseCase(repo),
                GetNoteByIdUseCase(repo),
                DeleteNoteUseCase(repo),
                UpdateNoteUseCase(repo),
                InsertNoteUseCase(repo)
            )
        )
    }
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
        setDateAndTimeText()// To get the current date and time
        onBackButtonClick()
        onShowEditFragmentBottomSheetDialog()// function to show the bottom sheet dialog
        onSaveButtonClicked()
        observeTextInputChange()
    }

// checking if it's on edit mode

    private fun isOnEditMode() {
        if (toEditNoteId != ConstantValues.NULL_ARGUMENT) {
            viewModel.getIsOnEditMode(true)
            viewModel.getNoteById(toEditNoteId)
        }
    }

    private fun onEditModeUiUpdate() {
        if (viewModel.isOnEditMode) {
            hideSaveBtn()
            updateTitle()
            updateNote()
        }
    }

    private fun hideSaveBtn(){
        binding.saveNoteImageButton.visibility= View.GONE
    }

    private fun updateTitle(){
        binding.textFieldForTitle.setText(viewModel.editingNote.title)
    }

    private fun updateNote(){
        binding.textFieldForNote.setText(viewModel.editingNote.text)
    }
//<-- checking if it's on edit mode

// pooping up the bottom sheet

    private fun onShowEditFragmentBottomSheetDialog() {
        binding.bottomSheetDialogBtn.setOnClickListener {
            showEditFragmentBottomSheetDialog()
        }
    }

    private fun showEditFragmentBottomSheetDialog() {
        val bottomSheetDialog = EditFragmentBottomSheetDialog()
        bottomSheetDialog.show(childFragmentManager.beginTransaction(), "Bottom Sheet")
    }
//<-- pooping up the bottom sheet

// observing view part of the fragment

    // get the date and tame
    private fun setDateAndTimeText() {
        viewModel.getNoteDateAndTime(getCurrentDateAndTime())
        binding.dateAndTimeText.text = getCurrentDateAndTime()
    }

    private fun getCurrentDateAndTime(): String {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yy_HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

    //<-- get the date and tame

    private fun observeTextInputChange() {
        observeTitleTextChange()
        observeNoteTextChange()
    }

    // text handling
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
    //<-- text handling

//<-- observing view part of the fragment

    // saving and navigating from the fragment
    private fun onBackButtonClick() {
        binding.navigateBackImageButton.setOnClickListener {
            navigateToMainFragment()
        }
    }

    // saving note
    private fun onSaveButtonClicked() {
        binding.saveNoteImageButton.setOnClickListener {
            if (isNoteInvalid()) makeToastError()
            else insertNoteAndNavigate()
        }
    }

    private fun isNoteInvalid(): Boolean {
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

    private fun insertNoteAndNavigate() {
        viewModel.insertNewNote()
        navigateToMainFragment()
    }

    //<-- saving note

    // navigating back to main fragment
    private fun navigateToMainFragment() {
        val action = R.id.action_editFragment_to_mainFragment
        findNavController().navigate(action)
    }
    //<-- navigating back to main fragment

//<-- saving and navigating from the fragment
}