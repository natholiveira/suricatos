package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.amazon.AmazonS3Service
import com.fiap.suricatos.enum.PostType
import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.exception.InvalidStatusException
import com.fiap.suricatos.model.Post
import com.fiap.suricatos.model.PostPhoto
import com.fiap.suricatos.model.PostReply
import com.fiap.suricatos.repository.CommentRepository
import com.fiap.suricatos.repository.PostPhotoRepository
import com.fiap.suricatos.repository.PostReplyRepository
import com.fiap.suricatos.repository.PostRepository
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import com.fiap.suricatos.response.PostResponse
import com.fiap.suricatos.security.JWTDecoder
import com.fiap.suricatos.service.AddressService
import com.fiap.suricatos.service.PostService
import com.fiap.suricatos.service.UserService
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime

@Service
class PostServiceImpl(
        private val postRepository: PostRepository,
        private val postReplyRepository: PostReplyRepository,
        private val postPhotoRepository: PostPhotoRepository,
        private val userService: UserService,
        private val addressService: AddressService,
        private val commentRepository: CommentRepository,
        private val amazonS3Service: AmazonS3Service,
        private val jwtDecoder: JWTDecoder
) : PostService {

    override fun createPost(postRequest: PostRequest, images: List<MultipartFile>, token: String): PostResponse {
        val user = userService.getUser(jwtDecoder.decodeJWT(token))
        val address = postRequest.address?.let { addressService.createAddress(it) }
        val post = postRepository.save(Post.toModel(postRequest, user!!, address))

        val imagesUrl = arrayListOf<String>()
        images.forEach {
            val url = amazonS3Service.uploadImageToAmazon(it)
            postPhotoRepository.save(PostPhoto.toModel(url, post))
            imagesUrl.add(url)
        }

        return PostResponse(post, null, arrayListOf(), imagesUrl)
    }

    override fun replyPost(postReplyRequest: PostReplyRequest, token: String): PostReply {
        val validStatus = arrayOf(Status.IN_PROGRESS, Status.CONCLUDED)

        if (!validStatus.contains(postReplyRequest.status))
            throw InvalidStatusException("Status ${postReplyRequest.status} is invalid")

        val user = userService.getUser(jwtDecoder.decodeJWT(token))

        val post = getPost(postReplyRequest.postId!!)
        val postUpdate = postRepository.save(post.copy(status = postReplyRequest.status))

        return postReplyRepository.save(PostReply.toModel(postReplyRequest, user!!, postUpdate))
    }

    fun getPost(postId: Long): Post =
            postRepository.findByIdOrNull(postId) ?: throw NotFoundExeption("Post $postId not found")

    override fun getPostResponse(postId: Long): PostResponse {
        val post = postRepository.findByIdOrNull(postId) ?: throw NotFoundExeption("Post $postId not found")
        val postPhotos = postPhotoRepository.findByPostId(postId).map { it.image }
        val postReply = postReplyRepository.findAllByPostId(post.id!!)
        val comments = commentRepository.findAllByPostId(post.id!!)

        return PostResponse(post, postReply, comments, postPhotos)
    }

    override fun getAllPostByUserAndStatus(token: String, status: Status, pageable: Pageable): List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        val user = userService.getUser(token)
        postRepository.findAllByUserIdAndStatus(user?.id!!, status, pageable).forEach {
            val postPhotos = postPhotoRepository.findByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val comments = commentRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, comments, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }

    override fun getAll(pageable: Pageable) : List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        postRepository.findAll(pageable).forEach {
            val postPhotos = postPhotoRepository.findByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val comments = commentRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, comments, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }

    override fun getAllByStatusAndCity(city: String, status: Status, pageable: Pageable) : List<PostResponse> {
        val postList = arrayListOf<PostResponse>()
        postRepository.findAllByAddress_cityAndStatus(city, status, pageable).forEach {
            val postPhotos = postPhotoRepository.findByPostId(it.id!!).map { it.image }
            val postReply = postReplyRepository.findAllByPostId(it.id!!)
            val comments = commentRepository.findAllByPostId(it.id!!)
            val postResponse = PostResponse(it, postReply, comments, postPhotos)

            postList.add(postResponse)
        }

        return postList
    }

    override fun update(postId: Long, postRequest: PostRequest, images: List<MultipartFile>): PostResponse =
        postRepository.findByIdOrNull(postId)?.let { post ->
            val address = postRequest.address?.let { addressService.createAddress(it) }
            postRepository.save(post.copy(
                    title = postRequest.title,
                    description = postRequest.description,
                    type = postRequest.type,
                    address = address,
                    updateAt = OffsetDateTime.now()
            ))

            postPhotoRepository.findByPostId(postId).forEach {
                postPhotoRepository.delete(it)
            }

            val imagesUrl = arrayListOf<String>()
            images.forEach {
                val url = amazonS3Service.uploadImageToAmazon(it)
                postPhotoRepository.save(PostPhoto.toModel(url, post))
                imagesUrl.add(url)
            }

            PostResponse(post, null, arrayListOf(), imagesUrl)
        } ?: throw NotFoundExeption("Post with id $postId not found")

    override fun delete(postId: Long) =
         postRepository.findByIdOrNull(postId)?.let {
             postRepository.delete(it)
         } ?: throw NotFoundExeption("Post with id $postId not found")

    override fun getAllCategory() = PostType.values().map { it.type }

    override fun like(postId: Long, like: Boolean) {
        postRepository.findByIdOrNull(postId)?.let { post ->
            var amountLike = post.like

            amountLike = if (like) amountLike+1 else amountLike-1

            postRepository.save(post.copy(like = amountLike))
        } ?: throw NotFoundExeption("Post with id $postId not found")
    }
}