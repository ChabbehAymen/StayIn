package com.example.stayin.presentation.ui.mainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stayin.R
import com.example.stayin.data.NoteItem
import com.example.stayin.databinding.NoteItemBinding
import com.example.stayin.presentation.utils.ConstantValues

class MainFragmentAdapter(private val itemInteractions: ItemInteractions): ListAdapter<NoteItem, MainFragmentAdapter.ViewHolder>(
    DiffCallBack
) {

    inner class ViewHolder(private var binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){

        lateinit var context: Context
        lateinit var mNoteItem: NoteItem
        fun bind(noteItem: NoteItem){
            mNoteItem = noteItem
            if (noteItem.image == ConstantValues.nullString)
                hideNoteImage()
            else
                loadNoteImage()
            bindNoteItems()
            onItemClickListener()
            onItemLongClickListener()

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

        private fun bindNoteItems(){
            bindViews()
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

        private fun bindViews(){
            bindNoteTitle(mNoteItem.title)
            bindNoteText(mNoteItem.text)
            bindNoteTag(mNoteItem.tag)
            bindNoteDate(mNoteItem.date)
            bindNoteBackgroundColor(mNoteItem.color)
        }

        private fun onItemClickListener() {
            binding.parentCardView.setOnClickListener {
                itemInteractions.onItemClickListener(mNoteItem)
            }
        }

        private fun onItemLongClickListener(){
            binding.parentCardView.setOnLongClickListener {
                itemInteractions.onItemLongClickListener(mNoteItem)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context)),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.context = holder.itemView.context
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

interface ItemInteractions{

    fun onItemClickListener(noteItem: NoteItem)

    fun onItemLongClickListener(noteItem: NoteItem)

}