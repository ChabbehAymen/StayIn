package com.example.stayin.presentation.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stayin.data.NoteDatabase
import com.example.stayin.databinding.FragmentMainBinding
import com.example.stayin.presentation.ui.SharedViewModel
import com.example.stayin.presentation.ui.SharedViewModelFactory
import com.example.stayin.presentation.utils.ConstantValues
import com.example.stayin.repository.RepoImplementation
import com.example.stayin.useCases.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    // I didn't know how to use Di, That's why I am using this horrible way /n
    private val viewModel by activityViewModels<SharedViewModel> {
        val repo = RepoImplementation(NoteDatabase.getDatabase(requireContext()).noteDao())
        SharedViewModelFactory(
            NoteUseCase(
                GetNotesUseCase(repo),
                DeleteNoteUseCase(repo),
                UpdateNoteUseCase(repo),
                InsertNoteUseCase(repo)
            )
        )
    }

    private val mAdapter = MainFragmentAdapter { noteItem ->
        viewModel.getNoteById(noteItem.id)
        navigateToEditFragment(noteItem.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        binding.addFloatingActionButton.setOnClickListener {
            navigateToEditFragment(ConstantValues.NULL_ARGUMENT)
        }

    }

    private fun setUpRecyclerView() {
        binding.mainFragmentRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }
        mAdapter.submitList(viewModel.getNotes())
    }

    private fun navigateToEditFragment(noteId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment()
            .setNoteId(noteId)
        findNavController().navigate(action)
    }
}