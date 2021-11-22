package com.fiap.suricatos.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fiap.suricatos.request.CommentRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "comment")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false, nullable = false,)
    val updateAt: OffsetDateTime,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt : OffsetDateTime,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false, referencedColumnName = "id")
    val post: Post? = null,

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
    val user: User? = null,

    @Column(name = "comment")
    val message : String? = null

) {
    companion object {
        fun toModel(post: Post, user: User, commentRequest: CommentRequest) = Comment(
                post = post,
                message = commentRequest.message,
                user = user,
                createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                updateAt = OffsetDateTime.now(ZoneOffset.UTC)
        )
    }
}