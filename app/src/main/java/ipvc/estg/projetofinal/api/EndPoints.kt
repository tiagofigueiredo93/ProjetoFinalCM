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

    //Buscar o report através do id
    @GET ("/myslim/api/report/{id}")
    fun getReportId(@Path("id") id: String?): Call<List<Report>>


    //Edição
    @FormUrlEncoded
    @POST("/myslim/api/report_edit/{id}")
    fun editar(@Path("id") id: String?,
               @Field("latitude") latitude: String?,
               @Field("longitude") longitude: String?,
               @Field("tipo") tipo: String?,
               @Field("descricao") descricao: String?,
               @Field("imagem") imagem: String?,
               @Field("utilizador_id") utilizador_id: Int?): Call<OutPutEditReport>
// fields passados no corpo do pedido e o id para passar na url

    //DELETE REPORT
    @POST("/myslim/api/delete/{id}")
    fun deleteReport(@Path("id") id: String?): Call<OutPutDeleteReport>



}