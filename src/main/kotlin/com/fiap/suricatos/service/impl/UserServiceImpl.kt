package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.model.User
import com.fiap.suricatos.model.UserAdress
import com.fiap.suricatos.model.UserPhone
import com.fiap.suricatos.model.UserPhoto
import com.fiap.suricatos.repository.*
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import com.fiap.suricatos.service.AddressService
import com.fiap.suricatos.service.UserService
import com.fiap.suricatos.util.Base64FileGenerator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val base64FileGenerator: Base64FileGenerator,
        private val userPhoneRepository: UserPhoneRepository,
        private val userAdressRepository: UserAdressRepository,
        private val userPhotoRepository: UserPhotoRepository,
        private val addressService: AddressService
) : UserService {
    override fun createUser(userRequest: UserRequest): UserResponse? {
        val user = userRepository.save(User.toModel(userRequest))

        val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

        userPhotoRepository.save(UserPhoto.toModel(userRequest.image, user))

        val address = addressService.createAddress(userRequest.addressRequest)
        userAdressRepository.save(UserAdress.toModel(user, address))

        return UserResponse(
                user,
                address,
                phone,
                userRequest.image
        )
    }


    override fun getUserResponse(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundExeption("User $userId not found")
        val phones = userPhoneRepository.findAllByUserId(userId)
        val address = userAdressRepository.findAllByUserId(userId)
        val images = userPhotoRepository.findAllByUserId(userId)

        return UserResponse(user, address.last().address!!, phones.last(), images.last().image!!)
    }

    override fun getUser(userId: Long): User? =
            userRepository.findByIdOrNull(userId) ?: throw NotFoundExeption("User $userId not found")
}