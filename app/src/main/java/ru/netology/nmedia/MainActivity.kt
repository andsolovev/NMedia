package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post = Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет! Это новая Нетология. Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению.",
            "21 мая 18:36",
            likedByMe = false,
            sharedByMe = false
        )

        binding.apply {
            author.text = post.author
            published.text = post.published
            postText.text = post.content
            likesCount.text = formatCount(post.likes)
            sharesCount.text = formatCount(post.shares)
            viewsCount.text = formatCount(post.views)

            likeLogo.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likeLogo.setImageResource(
                    if (post.likedByMe) R.drawable.like_logo_red else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount.text = formatCount(post.likes)
            }

            shareLogo.setOnClickListener {
                shareLogo.setImageResource(R.drawable.ic_share_24_blue)
                post.shares+=100
                sharesCount.text = formatCount(post.shares)
            }
        }
    }

    private fun formatCount(count: Int): String {
        return when {
            count in 1000..1099 -> {
                (count/1000).toString() + "K"
            }
            count in 1100..9499 -> {
                (((count/1000.0)*10).roundToInt()/10.0).toString() + "K"
            }
            count in 9500..999999 -> {
                (count/1000).toString() + "K"
            }
            count > 1000000 -> {
                (count/1_000_000).toString() + "M"
            }
            else -> {
                count.toString()
            }
        }
    }
}