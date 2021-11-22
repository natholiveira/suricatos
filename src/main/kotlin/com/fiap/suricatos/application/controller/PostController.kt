package com.fiap.suricatos.application.controller

import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.exception.BadRequestExceptioin
import com.fiap.suricatos.request.LikeRequest
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import com.fiap.suricatos.service.PostService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
class PostController(
        private val postService: PostService
) {

    @PostMapping("/api/post")
    @ApiOperation(value = "Create new Post")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "New post created"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun createPost(
            @RequestBody @Valid postRequest: PostRequest,
            bindingResult: BindingResult,
            @RequestPart(value = "images") images: List<MultipartFile>
    ) =
        if (bindingResult.hasErrors()) {
            throw BadRequestExceptioin()
        } else postService.createPost(postRequest, images)

    @PostMapping("/api/post-reply")
    @ApiOperation(value = "Create new Post Reply")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "New post reply created"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun createPostReply(@RequestBody @Valid postReplyRequest: PostReplyRequest, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else postService.replyPost(postReplyRequest)

    @GetMapping("/api/post")
    @ApiOperation(value = "List All post in the base")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post list"),
            ApiResponse(code = 400, message = "Bad Request")
    ))
    fun getAll(@PageableDefault(sort = arrayOf("createdAt"),
            direction = Sort.Direction.DESC,
            page = 0,
            size = 10) pageable: Pageable) =
            postService.getAll(pageable)

    @GetMapping("/api/post/{id}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List All post by User id and Status")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post list"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "User not Found")
    ))
    fun getAllByIdAndStatus(@PathVariable id: Long,
                   @PathVariable status: Status,
                   @PageableDefault(
                   sort = arrayOf("createdAt"),
                   direction = Sort.Direction.DESC,
                   page = 0,
                   size = 10) pageable: Pageable) = postService.getAllPostByUserAndStatus(id, status, pageable)

    @GetMapping("/api/post/city/{city}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List All post by City and Status")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post list"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "City not Found")
    ))
    fun getAllByIdAndCity(@PathVariable city: String,
                   @PathVariable status: Status,
                   @PageableDefault(
                           sort = arrayOf("createdAt"),
                           direction = Sort.Direction.DESC,
                           page = 0,
                           size = 10) pageable: Pageable) = postService.getAllByStatusAndCity(city, status, pageable)

    @GetMapping("/api/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Post by Id")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "Post not found")
    ))
    fun getById(@PathVariable id: Long,
                   @PageableDefault(
                                  sort = arrayOf("createdAt"),
                                  direction = Sort.Direction.DESC,
                                  page = 0,
                                  size = 10) pageable: Pageable) = postService.getPostResponse(id)

    @DeleteMapping("/api/post")
    @ApiOperation(value = "Delete post")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Delete post"),
            ApiResponse(code = 404, message = "Post not found")
    ))
    fun delete(@PathVariable postId: Long) =
            postService.delete(postId)

    @GetMapping("/api/post/category")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all category")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post"),
    ))
    fun getAllCategory() = postService.getAllCategory()

    @PostMapping("/api/post/{postId}/like")
    @ApiOperation(value = "Like or Unlike Post")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun like(@RequestBody @Valid likeRequest: LikeRequest, @PathVariable postId: Long, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else postService.like(postId, likeRequest.like)

    @PutMapping("/api/post/{postId}")
    @ApiOperation(value = "Update Post")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Update post"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun update(
            @RequestBody @Valid postRequest: PostRequest,
            bindingResult: BindingResult,
            @PathVariable postId: Long,
            @RequestPart(value = "images") images: List<MultipartFile>
    ) = if (bindingResult.hasErrors()) {
            throw BadRequestExceptioin()
        } else postService.update(postId, postRequest, images)
}