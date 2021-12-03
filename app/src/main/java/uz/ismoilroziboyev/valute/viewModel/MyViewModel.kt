package uz.ismoilroziboyev.valute.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ismoilroziboyev.valute.appdatabase.AppDatabase
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.retrofit.ApiClient
import uz.mobiler.workmanager.utils.NetworkHelper

class MyViewModel : ViewModel() {


    fun getValutesList(context: Context): MutableLiveData<List<ValuteModel>> {
        val mutableLiveData = MutableLiveData<List<ValuteModel>>()
        val networkHelper = NetworkHelper(context)
        val appDatabase = AppDatabase.getInstance(context)

        if (networkHelper.isNetworkConnected()) {
            ApiClient.apiService.getListValutes().enqueue(object : Callback<List<ValuteModel>> {

                override fun onResponse(
                    call: Call<List<ValuteModel>>,
                    response: Response<List<ValuteModel>>
                ) {
                    if (response.isSuccessful) {
                        mutableLiveData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<ValuteModel>>, t: Throwable) {

                }
            })
        } else {
            val allHistoriesList = appDatabase.valuteModelDao().getAllHistoriesList()

            if (allHistoriesList.isNotEmpty()) {
                mutableLiveData.value = allHistoriesList.last().list
            }
        }

        return mutableLiveData
    }

}