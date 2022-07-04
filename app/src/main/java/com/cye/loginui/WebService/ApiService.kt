package com.cye.loginui.WebService

import com.cye.loginui.model.ApiDataModel
import com.cye.loginui.model.UserModel
import retrofit2.http.*

interface ApiService {

    @GET("/api/user/user-details/{id}")
    suspend fun getUserDetails(@Path("id") uId: String): ApiDataModel

    @POST("/api/user/user-register")
    suspend fun userRegister(@Body userModel: UserModel): ApiDataModel

    @POST("/api/user/user-login")
    suspend fun userLogin(@Body userModel: UserModel): ApiDataModel
}