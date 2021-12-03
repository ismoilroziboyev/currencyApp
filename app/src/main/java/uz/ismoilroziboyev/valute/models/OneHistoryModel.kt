package uz.ismoilroziboyev.valute.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class OneHistoryModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val list: List<ValuteModel>
)