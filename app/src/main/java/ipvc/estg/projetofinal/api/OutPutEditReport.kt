package ipvc.estg.projetofinal.api

data class OutPutEditReport(
    val latitude: String,
    val longitude: String,
    val tipo: String,
    val descricao: String,
    val imagem: String,
    val utilizador_id: String,
    val status: String,
    val MSG: String
)