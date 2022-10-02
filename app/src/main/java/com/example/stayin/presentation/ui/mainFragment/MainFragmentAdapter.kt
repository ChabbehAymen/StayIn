package com.example.stayin.presentation.ui.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stayin.R
import com.example.stayin.data.NoteItem
import com.example.stayin.databinding.NoteItemBinding
import com.example.stayin.presentation.utils.ConstantValues

class MainFragmentAdapter(private val onItemClicked: (NoteItem) -> Unit): ListAdapter<NoteItem, MainFragmentAdapter.ViewHolder>(
    DiffCallBack
) {

    inner class ViewHolder(private var binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(noteItem: NoteItem){
            if (noteItem.image == ConstantValues.nullString)
                hideNoteImage()
            else
                loadNoteImage()
            bindNoteItems(noteItem)
            onItemClickListener(noteItem)
        }

        private fun setColor(color: String): Int {
            return when (color) {
                ConstantValues.blue -> R.color.blue_100
                ConstantValues.green -> R.color.green_100
                ConstantValues.red -> R.color.red_100
                ConstantValues.yellow -> R.color.yellow_100
                else -> R.color.white
            }
        }

        private fun hideNoteImage() {
            binding.noteImage.visibility = View.GONE
        }

        private fun loadNoteImage(){
            binding.noteImage.setImageResource(R.drawable.test_image)
        }

        private fun bindNoteItems(noteItem: NoteItem){
            bindNoteTitle(noteItem.title)
            bindNoteText(noteItem.text)
            bindNoteTag(noteItem.tag)
            bindNoteDate(noteItem.date)
            bindNoteBackgroundColor(noteItem.color)
        }

        private fun bindNoteTitle(noteTitle: String){
            binding.noteTitle.text = noteTitle
        }

        private fun bindNoteText(noteText: String){
            binding.noteText.text = noteText
        }

        private fun bindNoteTag(noteTag: String){
            binding.noteTag.text = noteTag
        }

        private fun bindNoteDate(noteDate: String){
            binding.noteDate.text = noteDate
        }

        private fun bindNoteBackgroundColor(noteBackgroundColor: String){
            binding.firstParent.setBackgroundResource(setColor(noteBackgroundColor))
        }

        private fun onItemClickListener(noteItem: NoteItem) {
            binding.parentCardView.setOnClickListener {
                onItemClicked.invoke(noteItem)
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