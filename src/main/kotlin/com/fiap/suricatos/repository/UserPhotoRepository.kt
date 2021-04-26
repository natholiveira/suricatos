package com.fiap.suricatos.repository

import com.fiap.suricatos.model.UserPhoto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPhotoRepository : JpaRepository<UserPhoto, Long> {
    fun findAllByUserId(userId: Long): List<UserPhoto>
}