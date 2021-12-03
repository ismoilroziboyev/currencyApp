package uz.ismoilroziboyev.valute.appdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.ismoilroziboyev.valute.models.OneHistoryModel


@Dao
interface ValuteModelDao {


    @Insert
    fun putNewOneHistory(oneHistoryModel: OneHistoryModel)

    @Query("select * from onehistorymodel")
    fun getAllHistoriesList(): List<OneHistoryModel>

    @Query("delete from onehistorymodel")
    fun deleteAllHistories()
}
