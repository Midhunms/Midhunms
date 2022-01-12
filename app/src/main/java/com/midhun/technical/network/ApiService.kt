package com.midhun.technical.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.midhun.technical.network.model.CreateUserRequestModel
import com.midhun.technical.network.model.base.ResponseBase
import com.midhun.technical.network.model.response.UserListResponseModel
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @GET(RequestURL.SERVICE_USERS)
    fun requestGetAllUsersAsync(@Query("page") pageNumber: Int): Deferred<ResponseBase<ArrayList<UserListResponseModel>>>

    @POST(RequestURL.SERVICE_USERS)
    fun requestCreateNewUserAsync(@Body createUser: CreateUserRequestModel): Deferred<ResponseBase<UserListResponseModel>>

    @DELETE(RequestURL.SERVICE_USERS+"/{id}")
    fun requestDeleteUserAsync(@Path("id") id: Int): Deferred<ResponseBase<String>>

    companion object {

        operator fun invoke(
            context: Context,
        ): ApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->

                    val request = chain.request()
                    val requestBuilder = request.newBuilder()
                    val mBuilder = requestBuilder.addHeader("Authorization","Bearer 5644fdbbbdd6a1cbea33ae5729fae270ec6b4ff8befa3eb02722a66bf22dfee2")
                    chain.proceed(mBuilder.build())
                }
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://gorest.co.in")
                .addCallAdapterFactory(CoroutineCallAdapter())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }
}