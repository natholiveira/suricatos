package com.fiap.suricatos.model

import com.fiap.suricatos.request.AddressRequest
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "address")
data class Address (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    val createdAt : OffsetDateTime,

    @LastModifiedDate
    @Column(name = "updated_at", updatable = false, nullable = false,)
    val updateAt: OffsetDateTime,

    @Column(name = "state")
    val state: String? = null,

    @Column(name = "number")
    val number: String? = null,

    @Column(name = "city")
    val city: String? = null,

    @Column(name = "complement")
    val complement: String? = null,

    @Column(name = "zip_code")
    val zipCode : String? = null,

    @Column(name = "street")
    val street : String? = null,

    @Column(name = "neighborhood ")
    val neighborhood  : String? = null
) {
    companion object {
        fun toModel(addressRequest: AddressRequest) = Address(
                city = addressRequest.city,
                street = addressRequest.street,
                neighborhood = addressRequest.neighborhood,
                complement = addressRequest.complement,
                zipCode = addressRequest.zipCode,
                number = addressRequest.number,
                createdAt = OffsetDateTime.now(ZoneOffset.UTC),
                updateAt =  OffsetDateTime.now(ZoneOffset.UTC)
        )
    }
}