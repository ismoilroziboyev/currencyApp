package uz.ismoilroziboyev.valute.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.ismoilroziboyev.valute.models.OneHistoryModel
import uz.ismoilroziboyev.valute.models.ValuteModel


@Database(entities = [OneHistoryModel::class], version = 1)
@TypeConverters(DataConvertor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun valuteModelDao(): ValuteModelDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {

                instance = Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }
}