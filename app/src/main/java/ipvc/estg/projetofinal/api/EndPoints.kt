package ipvc.estg.projetofinal.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EndPoints {

    @GET ("/myslim/api/utilizador")
    fun getUtilizadores(): Call<List<Utilizador>>

    @GET ("/myslim/api/report")
    fun getReports(): Call<List<Report>>

    @FormUrlEncoded
    @POST("myslim/api/login")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<OutputLogin>

}