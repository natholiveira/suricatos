package com.fiap.suricatos.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "post_reply")
data class PostReply (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt : OffsetDateTime,

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false, nullable = false,)
    val updateAt: OffsetDateTime,

    @Column(name = "external_link")
    val externalLink: String? = null,

    @Column(name = "external_protocol")
    val externalProtocol: String? = null,

    @Column(name = "description")
    val description: String? = null,

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
    val user: User? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false, referencedColumnName = "id")
    val post: Post? = null,
) {
    companion object {
        fun toModel(postReplyRequest: PostReplyRequest, user: User, post: Post) = PostReply (
                externalLink = postReplyRequest.externalLink,
                externalProtocol = postReplyRequest.externalProtocal,
                user = user,
                post = post,
                description = postReplyRequest.description,
                createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
        )
    }
}