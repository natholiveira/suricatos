package com.fiap.suricatos.service.impl

import com.fiap.suricatos.model.Address
import com.fiap.suricatos.repository.AddressRepository
import com.fiap.suricatos.request.AddressRequest
import com.fiap.suricatos.service.AddressService
import org.springframework.stereotype.Service

@Service
class AddressServiceImpl(
        private val addressRepository: AddressRepository
) : AddressService {
    override fun createAddress(addressRequest: AddressRequest): Address =
        addressRepository.save(Address.toModel(addressRequest))
}