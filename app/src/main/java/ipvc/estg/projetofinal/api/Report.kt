package ipvc.estg.projetofinal.api

data class Report (
    val id: Int,
    val latitude: Float,
    val longitude: Float,
    val tipo: String,
    val descricao: String,
    val imagem: String,
    val utilizador_id: Int
    )

