package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.model.Comment
import com.fiap.suricatos.repository.CommentRepository
import com.fiap.suricatos.repository.PostRepository
import com.fiap.suricatos.repository.UserRepository
import com.fiap.suricatos.request.CommentRequest
import com.fiap.suricatos.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(
    @Autowired
    private val commentRepository: CommentRepository,
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val postRepository: PostRepository
) : CommentService {
    override fun create(postId: Long, commentRequest: CommentRequest): Comment =
        postRepository.findByIdOrNull(postId)?.let { post ->
            userRepository.findByIdOrNull(commentRequest.userId)?.let { user ->
                commentRepository.save(Comment.toModel(post, user, commentRequest))
            } ?: throw NotFoundExeption("User with id ${commentRequest.userId} not found")
        } ?: throw NotFoundExeption("Post with id $postId not found")

    override fun update(commentId: Long, commentRequest: CommentRequest): Comment =
            commentRepository.findByIdOrNull(commentId)?.let { comment ->
                commentRepository.save(comment.copy(message = commentRequest.message))
            } ?: throw NotFoundExeption("Comment with id $commentId not found")


    override fun delete(commentId: Long) =
            commentRepository.findByIdOrNull(commentId)?.let { comment ->
                commentRepository.delete(comment)
            } ?: throw NotFoundExeption("Comment with id $commentId not found")

    override fun getAll(postId: Long): List<Comment> =
            postRepository.findByIdOrNull(postId)?.let {
                commentRepository.findAllByPostId(postId)
            } ?: throw NotFoundExeption("Post with id $postId Not found")

}