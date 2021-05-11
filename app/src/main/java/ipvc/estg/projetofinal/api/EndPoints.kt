package ipvc.estg.projetofinal.api


import retrofit2.Call
import retrofit2.http.*

interface EndPoints {



    @GET ("/smartcity/api/utilizador")
    fun getUtilizadores(): Call<List<Utilizador>>

    @GET ("/myslim/api/report3")
    fun getReports(): Call<List<Report>>

    @FormUrlEncoded
    @POST("/myslim/api/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Call<OutputLogin>
    //username e password vai no corpo do pedido


    //EndPoint para criar um report
    @FormUrlEncoded
    @POST("/myslim/api/report_insere")
    fun report(@Field("latitude") latitude: String?,
                 @Field("longitude") longitude: String?,
                 @Field("tipo") tipo: String?,
                 @Field("descricao") descricao: String?,
                 @Field("imagem") imagem: String?,
                @Field("utilizador_id") utilizador_id: Int): Call<OutPutReport>


}