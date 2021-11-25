package com.fiap.suricatos.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fiap.suricatos.enum.Status
import com.fiap.suricatos.exception.BadRequestExceptioin
import com.fiap.suricatos.request.LikeRequest
import com.fiap.suricatos.request.PostReplyRequest
import com.fiap.suricatos.request.PostRequest
import com.fiap.suricatos.response.PostResponse
import com.fiap.suricatos.service.PostService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
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
    @ApiOperation(value = "Create new Post", notes = "value to send in post param: \n" +POST_REQUEST_EXAMPLE,
            authorizations = [Authorization(value = "Bearer {token}")])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "New post created"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun createPost(
            @RequestParam("files")  files: List<MultipartFile>,
            @RequestParam(value = "post") post: String,
            @RequestHeader("Authorization") token: String
    ): PostResponse {
        val postRequest = jacksonObjectMapper().readValue(post, PostRequest::class.java)

        return postService.createPost(postRequest, files, token)
    }

    @PostMapping("/api/post-reply")
    @ApiOperation(value = "Create new Post Reply", authorizations = [Authorization(value = "Bearer {token}")])
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "New post reply created"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun createPostReply(@RequestBody @Valid postReplyRequest: PostReplyRequest, bindingResult: BindingResult, @RequestHeader("Authorization") token: String) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else postService.replyPost(postReplyRequest, token)

    @GetMapping("/api/post")
    @ApiOperation(value = "List All post in the base", authorizations = [Authorization(value = "Bearer {token}")])
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

    @GetMapping("/api/post/user/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List All post of User logged and Status", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post list"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "User not Found")
    ))
    fun getAllByIdAndStatus(
                   @PathVariable status: Status,
                   @PageableDefault(
                   sort = arrayOf("createdAt"),
                   direction = Sort.Direction.DESC,
                   page = 0,
                   size = 10) pageable: Pageable,
                   @RequestHeader("Authorization") token: String) = postService.getAllPostByUserAndStatus(token, status, pageable)

    @GetMapping("/api/post/city/{city}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List All post by City and Status", authorizations = [Authorization(value = "Bearer {token}")])
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

    @GetMapping("/api/post/user/city/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List All post by City of logged user and Status", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post list"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "City not Found")
    ))
    fun getAllByUserAndCity(
                          @PathVariable status: Status,
                          @PageableDefault(
                                  sort = arrayOf("createdAt"),
                                  direction = Sort.Direction.DESC,
                                  page = 0,
                                  size = 10) pageable: Pageable,
                            @RequestHeader("Authorization") token: String) = postService.getAllUserAddressByUserAndStatus(token, status, pageable)

    @GetMapping("/api/post/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Post by Id", authorizations = [Authorization(value = "Bearer {token}")])
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
    @ApiOperation(value = "Delete post", authorizations = [Authorization(value = "Bearer {token}")])
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Delete post"),
            ApiResponse(code = 404, message = "Post not found")
    ))
    fun delete(@PathVariable postId: Long) =
            postService.delete(postId)

    @GetMapping("/api/post/category")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all category", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return post"),
    ))
    fun getAllCategory() = postService.getAllCategory()

    @PostMapping("/api/post/{postId}/like")
    @ApiOperation(value = "Like or Unlike Post", notes = "if like = true like post | if like = false deslike post", authorizations = [Authorization(value = "Bearer {token}")])
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "OK"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun like(@RequestBody @Valid likeRequest: LikeRequest, @PathVariable postId: Long, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else postService.like(postId, likeRequest.like)

    @PutMapping("/api/post/{postId}")
    @ApiOperation(value = "Update Post", notes = "value to send in post param: \n" + POST_REQUEST_EXAMPLE,
            authorizations = [Authorization(value = "Bearer {token}")])
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Update post"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun update(
            @RequestParam("files")  files: List<MultipartFile>,
            @RequestParam(value = "post") post: String,
            @PathVariable postId: Long
    ): PostResponse {
        val postRequest = jacksonObjectMapper().readValue(post, PostRequest::class.java)

        return postService.update(postId, postRequest, files)
    }

    companion object {
        const val POST_REQUEST_EXAMPLE = """
            {
                "slug": "slug",
                "title": "title",
                "description": "description",
                "userId": 2,
                "address": {
                    "state": "SP",
                    "number": "123",
                    "city": "Osasco",
                    "complement": "casa 2",
                    "zipCode": "06216170",
                    "street": "Rua Sebastião Tirador Fernades",
                    "neighborhood": "Presidente Altino"
                },
                "type": "ALAGAMENTO"
            }
        """
    }
}