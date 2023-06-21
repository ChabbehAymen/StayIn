package com.example.stayin.presentation.ui.mainFragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    private val notesList = viewModel.getAllNotes()
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
        onAddFabBtnCLickListener()
    }

    private fun setUpRecyclerView() {
        binding.mainFragmentRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }
        mAdapter.submitList(notesList)
    }

    private fun navigateToEditFragment(noteId: Int) {
        val action = MainFragmentDirections.actionMainFragmentToEditFragment()
            .setNoteId(noteId)
        findNavController().navigate(action)
    }

    private fun onAddFabBtnCLickListener(){
        binding.addFloatingActionButton.setOnClickListener {
            navigateToEditFragment(ConstantValues.NULL_ARGUMENT)
        }
    }

    override fun onItemClickListener(noteItem: NoteItem) {
        viewModel.getNoteItemById(noteItem.id)
        navigateToEditFragment(noteItem.id)
    }

    override fun onItemLongClickListener(noteItem: NoteItem, position: Int) {
        hideAddFabBtnAnimation()
        showDeleteFabBtnAnimation()
        deleteFabClickListener(noteItem, position)
    }

    private fun deleteFabClickListener(noteItem: NoteItem, position: Int){
        binding.deleteFloatingActionButton.setOnClickListener {
            viewModel.deleteNoteItem(noteItem)
            mAdapter.submitList(viewModel.getAllNotes())
            hideDeleteFaBtnAnimation()
            showAddFabBtnAnimation()
        }

    }

    private fun showDeleteFabBtn(){
        binding.deleteFloatingActionButton.visibility = View.VISIBLE
    }

    private fun hideDeleteFabBtn(){
        binding.deleteFloatingActionButton.visibility = View.GONE
    }

    private fun showAddFabBtn(){
        binding.addFloatingActionButton.visibility = View.VISIBLE
    }

    private fun hideAddFabBtn(){
        binding.addFloatingActionButton.visibility = View.GONE
    }

    private fun showAddFabBtnAnimation(){
        showAddFabBtn()
        ObjectAnimator.ofFloat(binding.addFloatingActionButton,View.TRANSLATION_Y,300f, 0f ).apply {
            start()
        }
    }
    private fun hideAddFabBtnAnimation(){
        ObjectAnimator.ofFloat(binding.addFloatingActionButton,View.TRANSLATION_Y,0f, 300f ).apply {
            start()
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    hideAddFabBtn()
                }
            })
        }
    }
    private fun showDeleteFabBtnAnimation(){
        showDeleteFabBtn()
        ObjectAnimator.ofFloat(binding.deleteFloatingActionButton,View.TRANSLATION_Y,300f, 0f ).apply {
         start()
        }
    }

    private fun hideDeleteFaBtnAnimation(){
        ObjectAnimator.ofFloat(binding.deleteFloatingActionButton,View.TRANSLATION_Y,0f, 300f ).apply {
            start()
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    hideDeleteFabBtn()
                }
            })
        }
    }
}