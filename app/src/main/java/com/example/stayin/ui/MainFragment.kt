package com.example.stayin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.stayin.R
import com.example.stayin.StayinApplication
import com.example.stayin.data.NoteItem
import com.example.stayin.databinding.FragmentMainBinding
import com.example.stayin.ui.adapters.MainFragmentAdapter
import com.example.stayin.ui.models.MainViewModel
import com.example.stayin.ui.models.MainViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity?.application as StayinApplication).noteDatabase.noteDao())
    }
    private val mAdapter = MainFragmentAdapter({})

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
            navigateToAddFragment()
        }

    }

    private fun setUpRecyclerView(){
        binding.mainFragmentRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }
        lifecycle.coroutineScope.launch {
            viewModel.getNotes().collect(){
                mAdapter.submitList(it)
            }
        }
    }


    private fun submittedList(): List<NoteItem> {
        val mutableList = mutableListOf<NoteItem>()
        for (i in 0..7) {
            mutableList.add(
                NoteItem(
                    title = "Home",
                    text = getString(R.string.text_for_test),
                    image = "NULL",
                    date = "25/10/2003",
                    color = "NULL",
                    tag = "TEST"
                )
            )
        }
        mutableList.add(differentNoteItem())
        return mutableList
    }
    private fun differentNoteItem(): NoteItem{
        return NoteItem(
            title = "I Got You",
            text = getString(R.string.text_for_test),
            image = "NULL",
            date = "25/10/2003",
            color = ConstantValues.white,
            tag = "NOTE"
        )
    }

    private fun submitList() {
        mAdapter.submitList(submittedList())
    }

    private fun navigateToAddFragment() {
        val action = R.id.action_mainFragment_to_editFragment
        findNavController().navigate(action)
    }
}