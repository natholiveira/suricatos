package com.fiap.suricatos.service.impl

import com.fiap.cyrela.exception.NotFoundExeption
import com.fiap.suricatos.amazon.AmazonS3Service
import com.fiap.suricatos.exception.DuplicatedUserException
import com.fiap.suricatos.model.*
import com.fiap.suricatos.repository.UserAdressRepository
import com.fiap.suricatos.repository.UserPhoneRepository
import com.fiap.suricatos.repository.UserPhotoRepository
import com.fiap.suricatos.repository.UserRepository
import com.fiap.suricatos.request.UserRequest
import com.fiap.suricatos.response.UserResponse
import com.fiap.suricatos.security.JWTDecoder
import com.fiap.suricatos.service.AddressService
import com.fiap.suricatos.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.OffsetDateTime


@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val amazonS3Service: AmazonS3Service,
        private val userPhoneRepository: UserPhoneRepository,
        private val userPhotoRepository: UserPhotoRepository,
        private val passwordEncoder: PasswordEncoder,
        private val addressService: AddressService,
        private val userAddressRepository: UserAdressRepository,
        private val jwtDecoder: JWTDecoder
) : UserService {
    override fun createWithImage(multipartFile: MultipartFile, userRequest: UserRequest): UserResponse? {

        val encryptedPassword = passwordEncoder.encode(userRequest.password)

        userRepository.findByEmail(userRequest.email)?.let {
            throw DuplicatedUserException("User with email ${userRequest.email} already exist")
        }

        val user = userRepository.save(User.toModel(encryptedPassword, userRequest))

        userRequest.address?.let {
            val address = addressService.createAddress(userRequest.address)
            userAddressRepository.save(UserAdress.toModel(user, address))
        }

        val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

        val url = amazonS3Service.uploadImageToAmazon(multipartFile)

        userPhotoRepository.save(UserPhoto.toModel(url, user))

        return UserResponse(
                user,
                phone,
                url
        )
    }

    override fun getUserResponse(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundExeption("User $userId not found")
        val phones = userPhoneRepository.findAllByUserId(userId)
        val images = userPhotoRepository.findAllByUserId(userId)

        return UserResponse(user, phones.lastOrNull(), images.lastOrNull()?.image!!)
    }

    override fun getUserLogged(token: String): UserResponse {
        val email = jwtDecoder.decodeJWT(token)
        val user = userRepository.findByEmail(email) ?: throw NotFoundExeption("User with email $email not found")
        val phones = userPhoneRepository.findAllByUserId(user.id!!)
        val images = userPhotoRepository.findAllByUserId(user.id!!)

        return UserResponse(user, phones.lastOrNull(), images.lastOrNull()?.image)
    }

    override fun getUser(token: String): User {
        val email = jwtDecoder.decodeJWT(token)
        return userRepository.findByEmail(email) ?: throw NotFoundExeption("User with email $email not found")
    }

    override fun create(userRequest: UserRequest): UserResponse {
        val encryptedPassword = passwordEncoder.encode(userRequest.password)

        userRepository.findByEmail(userRequest.email)?.let {
            throw DuplicatedUserException("User with email ${userRequest.email} already exist")
        }

        val user = userRepository.save(User.toModel(encryptedPassword, userRequest))

        val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

        userRequest.address?.let {
            val address = addressService.createAddress(userRequest.address)
            userAddressRepository.save(UserAdress.toModel(user, address))
        }

        return UserResponse(
                user,
                phone,
                null
        )
    }

    override fun updateImage(multipartFile: MultipartFile, token: String): UserResponse {
        val user = getUser(token)
        val url = amazonS3Service.uploadImageToAmazon(multipartFile)

        val phone = userPhoneRepository.findAllByUserId(user?.id!!)

        userPhotoRepository.save(UserPhoto.toModel(url, user))

        return UserResponse(
                user,
                phone.lastOrNull(),
                url
        )
    }

    override fun update(token: String, userRequest: UserRequest): UserResponse {
        val email = jwtDecoder.decodeJWT(token)

        userRepository.findByEmail(email)?.let { user ->

            userRepository.save(user.copy(
                    name = userRequest.name,
                    biography = userRequest.biography,
                    birthday = userRequest.birthday,
                    updateAt = OffsetDateTime.now(),
                    email = userRequest.email
            )
            )

            val phone = userPhoneRepository.save(UserPhone.toModel(userRequest.phone, user))

            val image = userPhotoRepository.findAllByUserId(userId = user?.id!!).lastOrNull()

            return UserResponse(
                    user,
                    phone,
                    image?.image
            )
        } ?: throw NotFoundExeption("User with email $email not found")
    }

    override fun findAll(): List<User> = userRepository.findAll()

}