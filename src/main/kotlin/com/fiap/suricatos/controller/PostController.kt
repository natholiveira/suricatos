package com.fiap.suricatos.controller

import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.exception.BadRequestExceptioin
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
    fun createPost(@RequestBody @Valid postRequest: PostRequest, bindingResult: BindingResult) =
        if (bindingResult.hasErrors()) {
            throw BadRequestExceptioin()
        } else postService.createPost(postRequest)

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

}