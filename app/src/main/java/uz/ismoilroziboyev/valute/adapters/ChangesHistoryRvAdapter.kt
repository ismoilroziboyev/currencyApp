package uz.ismoilroziboyev.valute.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ismoilroziboyev.valute.databinding.ItemChangesHistoryRvBinding
import uz.ismoilroziboyev.valute.models.ValuteModel

class ChangesHistoryRvAdapter(var list: List<ValuteModel>) :
    RecyclerView.Adapter<ChangesHistoryRvAdapter.MyViewHolder>() {


    inner class MyViewHolder(val itemChangesHistoryRvBinding: ItemChangesHistoryRvBinding) :
        RecyclerView.ViewHolder(itemChangesHistoryRvBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemChangesHistoryRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemChangesHistoryRvBinding.item = list[position]
    }

    override fun getItemCount(): Int = list.size
}