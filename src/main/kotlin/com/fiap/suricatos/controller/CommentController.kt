package com.fiap.suricatos.controller

import com.fiap.suricatos.exception.BadRequestExceptioin
import com.fiap.suricatos.request.CommentRequest
import com.fiap.suricatos.service.CommentService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Authorization
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class CommentController(
        private val commentService: CommentService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/comment/post/{postId}")
    @ApiOperation(value = "Create new Comment", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Created new Comment"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "Post not found"),
    ))
    fun create(@RequestBody @Valid commentRequest: CommentRequest, @PathVariable postId: Long, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else commentService.create(postId, commentRequest)


    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/comment/{commentId}")
    @ApiOperation(value = "Update Comment", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Update Comment"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "Comment not found"),
    ))
    fun update(@RequestBody @Valid commentRequest: CommentRequest, @PathVariable commentId: Long, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else commentService.update(commentId, commentRequest)

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/comment/{commentId}")
    @ApiOperation(value = "Delete Comment", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Delete Comment"),
            ApiResponse(code = 404, message = "Comment not found"),
    ))
    fun delete(@PathVariable commentId: Long) =
           commentService.delete(commentId)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/comment/post/{postId}")
    @ApiOperation(value = "Get all Comments by Post", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Get all comments post"),
            ApiResponse(code = 404, message = "Post not found"),
    ))
    fun getAllByPost(@PathVariable postId: Long) =
            commentService.getAll(postId)
}