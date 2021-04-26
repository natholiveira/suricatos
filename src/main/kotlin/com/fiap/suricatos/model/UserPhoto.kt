package com.fiap.suricatos.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Base64
import javax.persistence.*

@Entity
@Table(name = "user_photo")
data class UserPhoto (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
        val user: User? = null,

        @Column(name = "image")
        val image: String? = null,

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        val createdAt : OffsetDateTime,

        @LastModifiedDate
        @Column(name = "updated_at", updatable = false, nullable = false,)
        val updateAt: OffsetDateTime,
) {
        companion object {
                fun toModel(image: String, user: User) = UserPhoto (
                        image = image,
                        user = user,
                        createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                        updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
                )
        }
}