package com.example.stayin.presentation.ui.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stayin.R
import com.example.stayin.data.NoteDatabase
import com.example.stayin.data.NoteItem
import com.example.stayin.databinding.FragmentMainBinding
import com.example.stayin.presentation.ui.mainFragment.model.MainFragmentVMFactory
import com.example.stayin.presentation.ui.mainFragment.model.MainFragmentViewModel
import com.example.stayin.presentation.utils.ConstantValues
import com.example.stayin.repository.RepoImplementation
import com.example.stayin.useCases.*

class MainFragment : Fragment(), ItemInteractions {

    private lateinit var binding: FragmentMainBinding

    // I didn't know how to use Di, That's why I am using this horrible way /n
    private val viewModel by viewModels<MainFragmentViewModel> {
        val repo = RepoImplementation(NoteDatabase.getDatabase(requireContext()).noteDao())
        MainFragmentVMFactory(
            NoteUseCase(
                GetNotesUseCase(repo),
                GetNoteByIdUseCase(repo),
                DeleteNoteUseCase(repo),
                UpdateNoteUseCase(repo),
                InsertNoteUseCase(repo)
            )
        )
    }

    private val mAdapter = MainFragmentAdapter(this)


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
        hideDeleteFabBtn()
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
        mAdapter.submitList(viewModel.getAllNotes())
    }

    private fun navigateToEditFragment(noteId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment()
            .setNoteId(noteId)
        findNavController().navigate(action)
    }

    override fun onItemClickListener(noteItem: NoteItem) {
        viewModel.getNoteItemById(noteItem.id)
        navigateToEditFragment(noteItem.id)
    }

    override fun onItemLongClickListener(noteItem: NoteItem) {
        animateAddFabBtn()
        animateDeleteFabBtn()
        deleteFabClickListener(noteItem)
    }

    private fun deleteFabClickListener(noteItem: NoteItem){
        binding.deleteFloatingActionButton.setOnClickListener {
            viewModel.deleteNoteItem(noteItem)
        }
    }

    private fun hideDeleteFabBtn(){
        binding.deleteFloatingActionButton.visibility = View.GONE
    }

    private fun hideAddFabBtn(){
        binding.addFloatingActionButton.visibility = View.GONE
    }

    private fun showDeleteFabBtn(){
        binding.deleteFloatingActionButton.visibility = View.VISIBLE
    }

    private fun animateAddFabBtn(){
        hideAddFabBtn()
    }
    private fun animateDeleteFabBtn(){
        showDeleteFabBtn()
    }
}