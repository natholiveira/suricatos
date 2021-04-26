package com.fiap.suricatos.repository

import com.fiap.suricatos.model.PostPhoto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostPhotoRepository : JpaRepository<PostPhoto, Long> {
    fun findAllByPostId(postId: Long): List<PostPhoto>
}