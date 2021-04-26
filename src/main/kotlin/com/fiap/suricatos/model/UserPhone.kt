package com.fiap.suricatos.model

import com.fiap.suricatos.enum.PhoneType
import com.fiap.suricatos.request.PhoneRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "user_phone")
data class UserPhone (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
        val user: User? = null,

        @Column(name = "ddd")
        val ddd: String? = null,

        @Column(name = "number")
        val number: String? = null,

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        val createdAt : OffsetDateTime,

        @LastModifiedDate
        @Column(name = "updated_at", updatable = false, nullable = false,)
        val updateAt: OffsetDateTime,

        @Column(name = "type")
        @Enumerated(EnumType.STRING)
        val type: PhoneType? = null,
) {
        companion object {
                fun toModel(phoneRequest: PhoneRequest, user: User) = UserPhone (
                        ddd = phoneRequest.ddd,
                        number = phoneRequest.number,
                        type = phoneRequest.type,
                        user = user,
                        createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                        updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
                )
        }
}