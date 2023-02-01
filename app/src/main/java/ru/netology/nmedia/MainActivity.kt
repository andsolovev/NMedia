package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this) { post ->
            binding.apply {
                author.text = post.author
                published.text = post.published
                postText.text = post.content
                likesCount.text = formatCount(post.likes)
                sharesCount.text = formatCount(post.shares)
                viewsCount.text = formatCount(post.views)
                likeLogo.setImageResource(if (post.likedByMe) R.drawable.like_logo_red else R.drawable.ic_baseline_favorite_border_24)
                shareLogo.setImageResource(if (post.sharedByMe) R.drawable.ic_share_24_blue else R.drawable.ic_baseline_share_24)
            }
        }

        binding.apply {
            shareLogo.setOnClickListener {
                Log.d("stuff", "share")
                viewModel.share()
            }
            likeLogo.setOnClickListener {
                Log.d("stuff", "like")
                viewModel.like()
            }
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