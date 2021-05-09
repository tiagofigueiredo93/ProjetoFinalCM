package ipvc.estg.projetofinal.api

data class Utilizador(
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val nif: Int
)