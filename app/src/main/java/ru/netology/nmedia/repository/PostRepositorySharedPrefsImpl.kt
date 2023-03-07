package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, typeToken)
            nextId = (posts.maxOfOrNull { post -> post.id } ?: 0) + 1
        } ?: run {
            posts = listOf(
                Post(
                    id = nextId++,
                    "Нетология. Университет интернет-профессий будущего",
                    "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.",
                    "",
                    "21 мая 18:36",
                    999,
                    5,
                    5,
                    likedByMe = false,
                    sharedByMe = false
                ),
                Post(
                    id = nextId++,
                    "Нетология. Университет интернет-профессий будущего",
                    "Затем появились курсы по дизайну, разработке, аналитике и управлению.",
                    "https://www.youtube.com/watch?v=WhWc3b3KhnY",
                    "21 мая 18:37",
                    999,
                    5,
                    5,
                    likedByMe = false,
                    sharedByMe = false
                ),
                Post(
                    id = nextId++,
                    "Нетология. Университет интернет-профессий будущего",
                    "Знаний хватит на всех! На следующей неделе разбираемся с основами дизайна.",
                    "",
                    "21 мая 18:38",
                    999,
                    5,
                    5,
                    likedByMe = false,
                    sharedByMe = false
                ),
                Post(
                    id = nextId++,
                    "Нетология. Университет интернет-профессий будущего",
                    "Подробности по ссылке: https://netology.ru",
                    "",
                    "21 мая 18:39",
                    999,
                    5,
                    5,
                    likedByMe = false,
                    sharedByMe = false
                )
            )
        }
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if (it.likedByMe) -1 else 1
            )
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                sharedByMe = true,
                shares = it.shares + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    override fun edit(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        sync()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    content = post.content,
                    published = "Now",
                    likes = 0,
                    shares = 0,
                    views = 0,
                    likedByMe = false,
                    sharedByMe = false
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
        data.value = posts
        sync()
    }

    private fun sync() {
        prefs.edit().apply {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}