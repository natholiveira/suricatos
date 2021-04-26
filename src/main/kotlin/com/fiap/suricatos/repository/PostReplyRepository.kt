package com.fiap.suricatos.repository

import com.fiap.suricatos.model.PostReply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostReplyRepository : JpaRepository<PostReply, Long> {
    fun findAllByPostId(postId: Long): List<PostReply>
}