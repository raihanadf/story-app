package com.raihan.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.raihan.story.data.model.dto.story.Story
import com.raihan.story.databinding.ItemStoryBinding
import com.raihan.story.presentation.fragment.story.list.StoryListFragmentDirections

class StoryAdapter :
    ListAdapter<Story, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(
                oldItem: Story, newItem: Story
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Story, newItem: Story
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

    class StoryViewHolder(val binding: ItemStoryBinding) :
        ViewHolder(binding.root) {

        fun bind(item: Story) {
            with(binding) {
                ivItemPhoto.load(item.photoUrl)
                tvItemName.text = item.name
                descriptionTv.text = item.description

                root.setOnClickListener {
                    val direction =
                        StoryListFragmentDirections.actionListStoryFragmentToStoryDetailFragment(
                            item
                        )
                    root.findNavController().navigate(direction)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): StoryViewHolder {
        val itemStory = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(itemStory)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

}

