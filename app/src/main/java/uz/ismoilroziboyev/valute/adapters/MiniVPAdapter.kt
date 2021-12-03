package uz.ismoilroziboyev.valute.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.ismoilroziboyev.valute.databinding.ItemViewPagerBinding
import uz.ismoilroziboyev.valute.models.ValuteModel

class MiniVPAdapter(val list: List<ValuteModel>) :
    RecyclerView.Adapter<MiniVPAdapter.MyViewHolder>() {


    inner class MyViewHolder(val itemViewPagerBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemViewPagerBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemViewPagerBinding.item = list[position]
    }

    override fun getItemCount(): Int = list.size

}