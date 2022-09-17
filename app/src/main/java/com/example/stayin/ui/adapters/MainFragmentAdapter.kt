package com.example.stayin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stayin.R
import com.example.stayin.data.NoteItem
import com.example.stayin.databinding.NoteItemBinding
import com.example.stayin.ui.ConstantValues

class MainFragmentAdapter(private val onItemClicked: (NoteItem) -> Unit): ListAdapter<NoteItem,MainFragmentAdapter.ViewHolder>(DiffCallBack) {

    inner class ViewHolder(private var binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(noteItem: NoteItem){
            binding.noteImage.visibility = View.GONE
            binding.noteTitle.text = noteItem.title
            binding.noteText.text = noteItem.text
            binding.noteTag.text = noteItem.tag
            binding.noteDate.text = noteItem.date
            binding.firstParent.setBackgroundColor(setColor(noteItem.color))

        }
        private fun setColor(color: String): Int{
            return when (color) {
                ConstantValues.blue -> R.color.blue_100
                ConstantValues.green -> R.color.green_100
                ConstantValues.red -> R.color.red_100
                ConstantValues.yellow -> R.color.yellow_100
                else -> R.color.white
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context)),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<NoteItem>(){
            override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem.text == newItem.text || oldItem.title == newItem.title
            }
        }

    }
}