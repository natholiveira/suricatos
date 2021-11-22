package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.amazon.AmazonS3Service
import com.fiap.suricatos.model.User
import com.fiap.suricatos.model.UserAdress
import com.fiap.suricatos.model.UserPhone
import com.fiap.suricatos.model.UserPhoto
import com.fiap.suricatos.repository.*
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import com.fiap.suricatos.service.AddressService
import com.fiap.suricatos.service.ChiperService
import com.fiap.suricatos.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val amazonS3Service: AmazonS3Service,
        private val userPhoneRepository: UserPhoneRepository,
        private val userAdressRepository: UserAdressRepository,
        private val userPhotoRepository: UserPhotoRepository,
        private val addressService: AddressService,
        private val chiperService: ChiperService
) : UserService {
    override fun create(multipartFile: MultipartFile, userRequest: UserRequest): UserResponse? {

        val encryptedPassword = chiperService.encrypted(userRequest.password)

        val user = userRepository.save(User.toModel(encryptedPassword, userRequest))

        val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

        val address = addressService.createAddress(userRequest.addressRequest)
        userAdressRepository.save(UserAdress.toModel(user, address))

        val url = amazonS3Service.uploadImageToAmazon(multipartFile)

        userPhotoRepository.save(UserPhoto.toModel(url, user))

        return UserResponse(
                user,
                address,
                phone,
                url
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

    override fun update(multipartFile: MultipartFile, userId: Long, userRequest: UserRequest): UserResponse =
            userRepository.findByIdOrNull(userId)?.let { user ->

                userRepository.save(user.copy(
                        name = userRequest.name,
                        biography = userRequest.biography,
                        birthday = userRequest.birthday,
                        updateAt = OffsetDateTime.now(),
                        email = userRequest.email
                    )
                )
                val address = addressService.createAddress(userRequest.addressRequest)
                val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

                val url = amazonS3Service.uploadImageToAmazon(multipartFile)

                userPhotoRepository.save(UserPhoto.toModel(url, user))

                return UserResponse(
                        user,
                        address,
                        phone,
                        url
                )
            } ?: throw NotFoundExeption("User with id $userId not found")

}