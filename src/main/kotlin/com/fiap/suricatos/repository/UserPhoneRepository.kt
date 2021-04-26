package com.fiap.suricatos.repository

import com.fiap.suricatos.model.UserPhone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPhoneRepository : JpaRepository<UserPhone, Long> {
    fun findAllByUserId(userId: Long): List<UserPhone>
}