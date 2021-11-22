package com.fiap.suricatos.controller

import com.fiap.suricatos.exception.BadRequestExceptioin
import com.fiap.suricatos.request.AddressRequest
import com.fiap.suricatos.service.AddressService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AdrressController(
        private val addressService: AddressService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/address")
    @ApiOperation(value = "Create new Address")
    @ApiResponses(value = arrayOf(
            ApiResponse(code = 201, message = "Created new Address"),
            ApiResponse(code = 400, message = "Bad Request"),
    ))
    fun createAddress(@RequestBody @Valid addressRequest: AddressRequest, bindingResult: BindingResult) =
            if (bindingResult.hasErrors()) {
                throw BadRequestExceptioin()
            } else addressService.createAddress(addressRequest)

}