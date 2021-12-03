package uz.ismoilroziboyev.valute.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.databinding.ItemAllRvBinding
import uz.ismoilroziboyev.valute.models.ValuteModel

class AllValutesRvAdapter(
    val onItemClickListener: OnItemClickListener,
    val list: List<ValuteModel>
) : RecyclerView.Adapter<AllValutesRvAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClickListener(item: ValuteModel)
    }

    inner class MyViewHolder(val itemAllRvBinding: ItemAllRvBinding) :
        RecyclerView.ViewHolder(itemAllRvBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemAllRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemAllRvBinding.item = list[position]

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.anim_for_rv)

        holder.itemView.animation = animation

        animation.start()

        holder.itemAllRvBinding

        Glide.with(holder.itemView.context)
            .load("https://nbu.uz/local/templates/nbu/images/flags/${list[position].code}.png")
            .into(holder.itemAllRvBinding.flagView)

        holder.itemAllRvBinding.calculatorBtn.setOnClickListener {
            onItemClickListener.onItemClickListener(
                list[position]
            )
        }
    }

    override fun getItemCount(): Int = list.size


}