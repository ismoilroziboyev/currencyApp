package uz.ismoilroziboyev.valute.appdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.ismoilroziboyev.valute.models.ValuteModel

class DataConvertor {


    @TypeConverter
    fun fromString(value: String): List<ValuteModel> {
        val listType = object : TypeToken<List<ValuteModel>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<ValuteModel>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}