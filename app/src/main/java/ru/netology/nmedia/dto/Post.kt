package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 1199,
    var shares: Int = 9999,
    val views: Int = 99999,
    var likedByMe: Boolean,
    var sharedByMe: Boolean
)
