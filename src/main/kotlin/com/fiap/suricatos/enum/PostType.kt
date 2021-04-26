package com.fiap.suricatos.enum

enum class PostType(val type: String) {
    ALAGAMENTO("Alagamento"),
    PROBLEMA_NO_ASFALTO("Problema com o asfalto"),
    PROPRIEDADE_PUBLICA_COM_DEFEITO_QUEBRADO("Propriedade pública com defeito/quebrado"),
    RUA_SEM_ILUMINACAO("Rua sem iluminação"),
    PROBLEMA_COM_SISTEMA_DE_ESGOTO("Problema com o sistema de esgoto"),
    DESLIZAMENTO("Deslizamento"),
    QUEDA_DE_ARVORE("Queda de Árvore"),
    ARVORE_COM_RISCO_DE_QUEDA("Árvore com risco de queda"),
    FALTA_DE_ENERGIA("Falta de energia"),
    OUTROS("Outros")
}