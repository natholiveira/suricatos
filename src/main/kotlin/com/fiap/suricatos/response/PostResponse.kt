package com.fiap.suricatos.response

import com.fiap.suricatos.model.Comment
import com.fiap.suricatos.model.Post
import com.fiap.suricatos.model.PostReply

data class PostResponse(
        val post: Post,
        val postReply: List<PostReply>?,
        val comments: List<Comment>,
        val images: List<String?>,
        val userImage: String?
)