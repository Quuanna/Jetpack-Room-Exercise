package com.example.exercise_room.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_room.database.Word
import com.example.exercise_room.databinding.ItemRecyclerViewBinding

class WordListAdapter(
    private val onItemOnClickListener: ((Int, Word) -> Unit)? = null
) : ListAdapter<Word, WordListAdapter.WordViewHolder>(WordsComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val db = getItem(position)
        holder.bind(db)
    }

    inner class WordViewHolder(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Word) {
            binding.textView.apply {
                this.text = item.englishWord
                setOnClickListener {
                    onItemOnClickListener?.invoke(adapterPosition, item)
                }
            }
        }
    }

    class WordsComparator : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id
        }
    }

}