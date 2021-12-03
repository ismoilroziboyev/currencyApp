package uz.ismoilroziboyev.valute.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ismoilroziboyev.valute.appdatabase.AppDatabase
import uz.ismoilroziboyev.valute.models.OneHistoryModel
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.retrofit.ApiClient
import uz.mobiler.workmanager.utils.NetworkHelper

class MyWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private lateinit var appDatabase: AppDatabase
    private lateinit var networkHelper: NetworkHelper

    override fun doWork(): Result {
        appDatabase = AppDatabase.getInstance(context)
        networkHelper = NetworkHelper(context)

        if (networkHelper.isNetworkConnected()) {
            ApiClient.apiService.getListValutes().enqueue(object : Callback<List<ValuteModel>> {

                override fun onResponse(
                    call: Call<List<ValuteModel>>,
                    response: Response<List<ValuteModel>>
                ) {
                    if (response.isSuccessful) {
                        val date =
                            appDatabase.valuteModelDao().getAllHistoriesList()
                                .reversed()[0].list[0].date

                        if (date != response.body()!![0].date) {

                            if (appDatabase.valuteModelDao().getAllHistoriesList().size > 10) {
                                appDatabase.valuteModelDao().deleteAllHistories()
                            }

                            val oneModel = OneHistoryModel(list = response.body()!!)
                            appDatabase.valuteModelDao().putNewOneHistory(oneModel)
                        }
                    }
                }

                override fun onFailure(call: Call<List<ValuteModel>>, t: Throwable) {

                }
            })
        }

        return Result.success()

    }
}