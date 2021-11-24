package com.fiap.suricatos.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import com.fiap.suricatos.service.UserService
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid


@RestController
class UserController(
        private val userService: UserService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/image")
    @ApiOperation(value = "Create new User", notes = "value to send in user param: \n" + USER_REQUEST_EXAMPLE)
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Created new User"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 409, message = "Email already exists")
    ))
    fun createUser(
            @RequestParam("user") user: String,
            @RequestParam("file")  file: MultipartFile
    ): UserResponse? {
        val userRequest = jacksonObjectMapper().readValue(user, UserRequest::class.java)

        return userService.createWithImage(file, userRequest)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user")
    @ApiOperation(value = "Create new User without image")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Created new User"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 409, message = "Email already exists")
    ))
    fun createUserWithoutImage(
            @RequestBody @Valid user: UserRequest
    ): UserResponse? {

        return userService.create(user)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/api/user/image")
    @ApiOperation(value = "Update User image")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Update"),
            ApiResponse(code = 400, message = "Bad Request"),
                    ApiResponse(code = 404, message = "Not found")
    ))
    fun updateImage(
            @RequestParam("file")  file: MultipartFile,
            @RequestHeader("Authorization") token: String
    ): UserResponse? {

        return userService.updateImage(file, token)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user/{id}")
    @ApiOperation(value = "Get user by Id", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return user"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "User Not Found")
    ))
    fun getUser(@PathVariable id: Long, @RequestHeader("Authorization") token: String) =
            userService.getUserResponse(id)

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user/logged")
    @ApiOperation(value = "Get user by Id", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return user"),
            ApiResponse(code = 400, message = "Bad Request"),
            ApiResponse(code = 404, message = "User Not Found")
    ))
    fun getUserLogged(@RequestHeader("Authorization") token: String) =
            userService.getUserLogged(token)

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/user")
    @ApiOperation(value = "Update User", notes = "value to send in user param: \n" + USER_REQUEST_EXAMPLE,
            authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Update User"),
            ApiResponse(code = 400, message = "Bad Request")
    ))
    fun update(
            @RequestBody @Valid user: UserRequest,
            @RequestHeader("Authorization") token: String
    ): UserResponse {
        return userService.update(token, user)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/user")
    @ApiOperation(value = "Get all user", authorizations = [Authorization(value = "Bearer {token}")])
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 200, message = "Return users")
    ))
    fun getAllUser() = userService.findAll()

    companion object {
        const val USER_REQUEST_EXAMPLE = """
           {
              "name": "nome",
              "birthday": "2021-11-21T19:46:50.598Z",
              "type": "CIDADAO",
              "biography": "biografia",
              "phone": {
                "ddd": "11",
                "number": "235264627",
                "type": "CELL"
              },
              "email": "vo.nathalia12@gmail.com",
              "password": "12345"
            }
        """
    }
}