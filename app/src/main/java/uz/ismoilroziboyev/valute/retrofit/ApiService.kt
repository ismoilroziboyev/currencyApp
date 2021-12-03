package uz.ismoilroziboyev.valute.retrofit

import retrofit2.Call
import retrofit2.http.GET
import uz.ismoilroziboyev.valute.models.ValuteModel

interface ApiService {

    @GET("uz/exchange-rates/json/")
    fun getListValutes(): Call<List<ValuteModel>>
}