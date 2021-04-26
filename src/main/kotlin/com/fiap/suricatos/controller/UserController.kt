package com.fiap.suricatos.controller

import com.fiap.suricatos.exception.BadRequestExceptioin
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.service.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
class UserController(
        private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/user")
    @ApiOperation(value = "Create new User")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Created new User"),
            ApiResponse(code = 400, message = "Bad Request")
    ))
    fun createUser(@RequestBody @Valid userRequest: UserRequest, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else userService.createUser(userRequest)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user/{id}")
    @ApiOperation(value = "Get user by Id")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return user"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "User Not Found")
    ))
    fun getUser(@PathVariable id: Long) =
            userService.getUserResponse(id)

}