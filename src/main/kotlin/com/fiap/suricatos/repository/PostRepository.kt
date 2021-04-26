package com.fiap.suricatos.repository

import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.model.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findAllByUserIdAndStatus(userId: Long, status: Status, pageable: Pageable): Page<Post>
    fun findAllByAddress_city(city: String, pageable: Pageable): Page<Post>
    fun findAllByAddress_cityAndStatus(city: String, status: Status, pageable: Pageable): Page<Post>
}