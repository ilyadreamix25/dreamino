package ua.ilyadreamix.amino.http.module

import ua.ilyadreamix.amino.http.interceptor.UserAgentInterceptor
import ua.ilyadreamix.amino.http.interceptor.SignaturesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.ilyadreamix.amino.http.interceptor.SessionInterceptor

object ApiModule {
    fun <T> provideService(repository: Class<T>): T =
        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .apply {
                        addInterceptor(SignaturesInterceptor())
                        addInterceptor(UserAgentInterceptor())
                        addInterceptor(SessionInterceptor())
                    }
                    .build()
            )
            .baseUrl("https://service.narvii.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(repository)
}