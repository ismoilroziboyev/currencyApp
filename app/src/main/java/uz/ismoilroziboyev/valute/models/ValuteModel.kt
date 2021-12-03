package uz.ismoilroziboyev.valute.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ValuteModel(
    val cb_price: String,
    @PrimaryKey
    val code: String,
    val date: String,
    val nbu_buy_price: String,
    val nbu_cell_price: String,
    val title: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ValuteModel

        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }

    override fun toString(): String {
        return "ValuteModel(cb_price='$cb_price', code='$code', date='$date', nbu_buy_price='$nbu_buy_price', nbu_cell_price='$nbu_cell_price', title='$title')"
    }


}