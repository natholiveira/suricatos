package com.fiap.suricatos.service

import com.fiap.suricatos.model.Address
import com.fiap.suricatos.request.AddressRequest


interface AddressService {
    fun createAddress(addressRequest: AddressRequest): Address
}