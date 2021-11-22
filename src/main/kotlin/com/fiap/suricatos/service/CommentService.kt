package com.fiap.suricatos.service

import com.fiap.suricatos.model.Comment
import com.fiap.suricatos.request.CommentRequest

interface CommentService {
    fun create(postId: Long, commentRequest: CommentRequest): Comment
    fun update(commentId: Long, commentRequest: CommentRequest): Comment
    fun delete(commentId: Long)
    fun getAll(postId: Long): List<Comment>
}