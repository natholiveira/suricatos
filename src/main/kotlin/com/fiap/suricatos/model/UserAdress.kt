package com.fiap.suricatos.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "user_adress")
data class UserAdress (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
    val user: User? = null,

    @ManyToOne
    @JoinColumn(name="address_id", nullable=false, referencedColumnName = "id")
    val address: Address? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt : OffsetDateTime,

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false, nullable = false,)
    val updateAt: OffsetDateTime,
) {
    companion object {
        fun toModel(user: User, address: Address?) = UserAdress (
                user = user,
                address = address,
                createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
        )
    }
}