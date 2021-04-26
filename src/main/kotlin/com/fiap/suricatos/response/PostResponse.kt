package com.fiap.suricatos.response

import com.fiap.suricatos.model.Post
import com.fiap.suricatos.model.PostReply

data class PostResponse(
        val post: Post,
        val postReply: List<PostReply>?,
        val images: List<String?>
)