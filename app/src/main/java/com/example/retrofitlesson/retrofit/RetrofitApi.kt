package com.example.retrofitlesson.retrofit

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @GET("products")
    suspend fun getAllProducts(): Products

    @GET("products/search")
    suspend fun searchProductsByName(@Query("q") name: String): Products

    @Headers("Content-Type: application/json")
    @GET("auth/products/search")
    suspend fun searchProductsByNameAuth(
        @Header("Authorization") authorization: String,
        @Query("q") name: String
    ): Products

    @POST("auth/login")
    suspend fun auth(@Body authRequest: AuthRequest): Response<User>
}