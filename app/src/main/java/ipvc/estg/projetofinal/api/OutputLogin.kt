package ipvc.estg.projetofinal.api


//Output do jason para o login
data class OutputLogin(
    val username: String,
    val password: String,
    val status: String,
    val Mensagem: String,
    val id: Int


)