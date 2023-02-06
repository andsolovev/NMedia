package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImplementation : PostRepository {

    private var posts = listOf(
        Post(
            1,
            "Нетология. Университет интернет-профессий будущего",
            "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.",
            "21 мая 18:36",
            999,
            5,
            5,
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            2,
            "Нетология. Университет интернет-профессий будущего",
            "Затем появились курсы по дизайну, разработке, аналитике и управлению.",
            "21 мая 18:37",
            999,
            5,
            5,
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            3,
            "Нетология. Университет интернет-профессий будущего",
            "Знаний хватит на всех! На следующей неделе разбираемся с основами дизайна.",
            "21 мая 18:38",
            999,
            5,
            5,
            likedByMe = false,
            sharedByMe = false
        ),
        Post(
            4,
            "Нетология. Университет интернет-профессий будущего",
            "Подробности по ссылке: https://netology.ru",
            "21 мая 18:39",
            999,
            5,
            5,
            likedByMe = false,
            sharedByMe = false
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if (it.likedByMe) -1 else 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                sharedByMe = !it.sharedByMe,
                shares = it.shares + if (it.sharedByMe) -1 else 1
            )
        }
        data.value = posts
    }
}