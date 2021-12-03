package uz.ismoilroziboyev.valute.utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences {


    companion object {
        private var instance: MySharedPreferences? = null
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
        private const val FILE_NAME = "shared_pref"

        fun getInstance(context: Context): MySharedPreferences {

            if (instance == null) {
                instance = MySharedPreferences()
                sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                editor = sharedPreferences?.edit()
            }

            return instance!!
        }
    }


    fun getCurrentCalcCurrency(): String {
        return sharedPreferences?.getString("code", "")!!
    }

    fun putNewCalcCurrency(currency: String) {
        editor?.putString("code", currency)?.commit()
    }


}