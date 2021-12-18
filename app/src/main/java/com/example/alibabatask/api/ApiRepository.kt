package com.example.alibabatask.api

import com.example.alibabatask.model.BaseResponseModel
import io.reactivex.rxjava3.core.Observable

class ApiRepository(private val apiService: APIService) {

     fun getData(): Observable<BaseResponseModel> {
        return apiService.getData()
    }

}