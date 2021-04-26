package com.fiap.suricatos.repository

import com.fiap.suricatos.model.UserAdress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAdressRepository : JpaRepository<UserAdress, Long> {
    fun findAllByUserId(userId: Long): List<UserAdress>
}