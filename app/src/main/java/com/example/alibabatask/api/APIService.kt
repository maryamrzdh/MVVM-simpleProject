package com.example.alibabatask.api

import com.example.alibabatask.model.BaseResponseModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface APIService {

    @GET("/api/users")
    fun getData(): Observable<BaseResponseModel>
}