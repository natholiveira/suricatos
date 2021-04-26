package com.fiap.suricatos.model

import com.fiap.suricatos.enum.UserType
import com.fiap.suricatos.request.UserRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "user_u")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt : OffsetDateTime,

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false, nullable = false,)
    val updateAt: OffsetDateTime,

    @Column(name = "birthday")
    val birthday: Date? = null,

    @Column(name = "biography")
    val biography: String? = null,

    @Column(name = "type")
    val type: String? = null,

) {
    companion object {
        fun toModel(userRequest: UserRequest) = User(
                name = userRequest.name,
                birthday = userRequest.birthday,
                biography = userRequest.biography,
                type = userRequest.type?.type,
                createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
        )
    }
}