package ipvc.estg.projetofinal.api

data class Utilizador(
    val id: Int,
    val username: String,
    val password: String,
    val email: String,
    val nif: Int
)