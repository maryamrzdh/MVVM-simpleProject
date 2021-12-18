package com.example.alibabatask.ui.fragment.first

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alibabatask.api.ApiRepository
import com.example.alibabatask.model.BaseResponseModel
import com.example.alibabatask.model.Datum
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(private val repository: ApiRepository): ViewModel(){
    private var _entity=MutableLiveData<MutableList<Datum>>()
//    val entity:LiveData<MutableList<Datum>>=_entity


    fun getData(){
        repository.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result: BaseResponseModel? -> _entity.setValue(result?.data) },
                { throwable: Throwable ->
                    _entity.setValue(null)
                    Log.e("", throwable.toString())
                }
            )
    }

    fun getModel():MutableLiveData<MutableList<Datum>>{
        return _entity
    }

}