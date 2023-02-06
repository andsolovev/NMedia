package ru.netology.nmedia.dto

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnLikeListener
import ru.netology.nmedia.databinding.CardPostBinding
import kotlin.math.roundToInt

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnLikeListener

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            likesCount.text = formatCount(post.likes)
            sharesCount.text = formatCount(post.shares)
            viewsCount.text = formatCount(post.views)
            likeLogo.setImageResource(if (post.likedByMe) R.drawable.like_logo_red else R.drawable.ic_baseline_favorite_border_24)
            likeLogo.setOnClickListener {
                onLikeListener(post)
            }
            shareLogo.setOnClickListener {
                onShareListener(post)
            }
            shareLogo.setImageResource(if (post.sharedByMe) R.drawable.ic_share_24_blue else R.drawable.ic_baseline_share_24)
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count in 1000..1099 -> {
                (count / 1000).toString() + "K"
            }
            count in 1100..9499 -> {
                (((count / 1000.0) * 10).roundToInt() / 10.0).toString() + "K"
            }
            count in 9500..999999 -> {
                (count / 1000).toString() + "K"
            }
            count > 1000000 -> {
                (count / 1_000_000).toString() + "M"
            }
            else -> {
                count.toString()
            }
        }
    }

}