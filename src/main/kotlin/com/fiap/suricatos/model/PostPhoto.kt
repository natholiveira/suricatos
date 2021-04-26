package com.fiap.suricatos.model

import java.util.Base64
import javax.persistence.*

@Entity
@Table(name = "post_photo")
data class PostPhoto (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name="post_id", nullable=false, referencedColumnName = "id")
        val post: Post? = null,

        @Column(name = "image")
        val image: String? = null,
) {
        companion object {
                fun toModel(image: String, post: Post) = PostPhoto (
                        image = image,
                        post = post
                )
        }
}