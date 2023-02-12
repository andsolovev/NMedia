package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostDiffCallBack
import ru.netology.nmedia.dto.PostViewHolder

//typealias OnButtonListener = (post: Post) -> Unit

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}