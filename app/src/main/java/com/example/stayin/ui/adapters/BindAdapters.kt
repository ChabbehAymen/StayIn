package com.example.stayin.ui.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stayin.data.NoteItem

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NoteItem>?){
    val adapter = recyclerView.adapter as MainFragmentAdapter
    adapter.submitList(data)
}