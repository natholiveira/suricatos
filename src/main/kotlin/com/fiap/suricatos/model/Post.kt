package com.fiap.suricatos.model

import com.fiap.suricatos.enum.PostType
import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.request.PostRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "post")
data class Post (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreatedDate
        val createdAt : OffsetDateTime,

        @LastModifiedDate
        @Column(name = "updated_at", updatable = false, nullable = false,)
        val updateAt: OffsetDateTime,

        @Column(name = "slug")
        val slug: String? = null,

        @Column(name = "title")
        val title: String? = null,

        @Column(name = "description")
        val description: String? = null,

        @ManyToOne
        @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
        val user: User? = null,

        @ManyToOne
        @JoinColumn(name="adress_id", nullable=false, referencedColumnName = "id")
        val address: Address? = null,

        @Enumerated(EnumType.STRING)
        @Column(name = "type")
        val type: PostType? = null,

        @Enumerated(EnumType.STRING)
        @Column(name = "status")
        val status: Status? = null,

        @Column(name = "like_post")
        val like: Int = 0,
) {
        companion object {
                fun toModel(postRequest: PostRequest, user: User, address: Address?) = Post (
                        slug = postRequest.slug,
                        title = postRequest.title,
                        description = postRequest.description,
                        type = postRequest.type,
                        user = user,
                        address = address,
                        status = Status.OPEN,
                        createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                        updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
                )
        }
}