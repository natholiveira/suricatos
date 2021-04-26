package com.fiap.suricatos.enum

enum class Status(val type: String) {
    OPEN("Aberto"),
    IN_PROGRESS("Em Andamento"),
    CONCLUDED("Concluído"),
    ACTIVE("Ativo"),
    INACTIVE("Inativo")
}