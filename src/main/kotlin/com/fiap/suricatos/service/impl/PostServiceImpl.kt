package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.exception.InvalidStatusException
import com.fiap.suricatos.model.Post
import com.fiap.suricatos.model.PostPhoto
import com.fiap.suricatos.model.PostReply
import com.fiap.suricatos.repository.PostPhotoRepository
import com.fiap.suricatos.repository.PostReplyRepository
import com.fiap.suricatos.repository.PostRepository
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import com.fiap.suricatos.response.PostResponse
import com.fiap.suricatos.service.AddressService
import com.fiap.suricatos.service.PostService
import com.fiap.suricatos.service.UserService
import com.fiap.suricatos.util.Base64FileGenerator
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
        private val postRepository: PostRepository,
        private val postReplyRepository: PostReplyRepository,
        private val postPhotoRepository: PostPhotoRepository,
        private val userService: UserService,
        private val addressService: AddressService,
        private val base64FileGenerator: Base64FileGenerator
) : PostService {
    override fun createPost(postRequest: PostRequest): PostResponse {
        val user = userService.getUser(postRequest.userId!!)
        val address = postRequest.address?.let { addressService.createAddress(it) }
        val post = postRepository.save(Post.toModel(postRequest, user!!, address))

        postRequest.images.forEach {
            postPhotoRepository.save(PostPhoto.toModel(it, post))
        }
        return PostResponse(post, null, postRequest.images)
    }

    override fun replyPost(postReplyRequest: PostReplyRequest): PostReply {
        val validStatus = arrayOf(Status.IN_PROGRESS, Status.CONCLUDED)

        if (!validStatus.contains(postReplyRequest.status))
            throw InvalidStatusException("Status ${postReplyRequest.status} is invalid")

        val user = userService.getUser(postReplyRequest.userId!!)

        val post = getPost(postReplyRequest.postId!!)
        val postUpdate = postRepository.save(post.copy(status = postReplyRequest.status))

        return postReplyRepository.save(PostReply.toModel(postReplyRequest, user!!, postUpdate))
    }

    fun getPost(postId: Long): Post =
            postRepository.findByIdOrNull(postId) ?: throw NotFoundExeption("Post $postId not found")

    override fun getPostResponse(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw NotFoundExeption("Post $postId not found")
        val postPhotos = postPhotoRepository.findAllByPostId(postId).map { it.image }
        val postReply = postReplyRepository.findAllByPostId(post.id!!)

        return PostResponse(post, postReply, postPhotos)
    }

    override fun getAllPostByUserAndStatus(userId: Long, status: Status, pageable: Pageable): List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        postRepository.findAllByUserIdAndStatus(userId, status, pageable).forEach {
            val postPhotos = postPhotoRepository.findAllByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }

    override fun getAll(pageable: Pageable) : List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        postRepository.findAll(pageable).forEach {
            val postPhotos = postPhotoRepository.findAllByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }

    override fun getAllByStatusAndCity(city: String, status: Status, pageable: Pageable) : List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        postRepository.findAllByAddress_cityAndStatus(city, status, pageable).forEach {
            val postPhotos = postPhotoRepository.findAllByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }
}