package com.example.alibabatask.di

import com.example.alibabatask.api.APIService
import com.example.alibabatask.api.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class APIModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): APIService=retrofit.create(APIService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: APIService) = ApiRepository(apiService)

//    @Singleton
//    @Provides
//    fun providesharedPreferences(@ApplicationContext context: Context): SharedPreferences =
//        context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    companion object {
        private const val BASE_URL = "https://reqres.in"
    }
}