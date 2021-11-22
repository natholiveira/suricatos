package com.fiap.suricatos.controller

import com.fiap.suricatos.request.LoginRequest
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @ApiOperation("Login Example", notes = "1. Username value is email of user \n " +
            "2. Token returned must be passed in the Authorization header of /api requests. Example = Bearer {token}")
    @PostMapping("/login")
    fun fakeLogin(@RequestBody loginRequest: LoginRequest) {
        throw IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.")
    }

    @ApiOperation("Logout Example")
    @PostMapping("/logout")
    fun fakeLogout() {
        throw IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.")
    }

}