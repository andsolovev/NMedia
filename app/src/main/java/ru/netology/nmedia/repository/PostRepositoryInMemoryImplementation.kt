package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImplementation : PostRepository {

    private var post = Post(
        1,
        "Нетология. Университет интернет-профессий будущего",
        "Привет! Это новая Нетология. Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению.",
        "21 мая 18:36",
        1199,
        5,
        5,
        likedByMe = false,
        sharedByMe = false
    )

    private val data = MutableLiveData(post)

    override fun get() = data

    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = post.likes + if (post.likedByMe) -1 else 1
        )
        data.value = post
    }

    override fun share() {
        post = post.copy(
            sharedByMe = !post.sharedByMe,
            shares = post.shares + if (post.sharedByMe) -1 else 1
        )
        data.value = post
    }
}