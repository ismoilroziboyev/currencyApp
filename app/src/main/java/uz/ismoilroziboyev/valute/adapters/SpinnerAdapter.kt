package uz.ismoilroziboyev.valute.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.databinding.ItemSpinnerBinding
import uz.ismoilroziboyev.valute.models.ValuteModel
import android.widget.ImageView

import android.widget.TextView


class SpinnerAdapter(context: Context, val list: List<ValuteModel>) :
    ArrayAdapter<ValuteModel>(context, R.layout.item_spinner) {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): ValuteModel {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = if (convertView == null) {
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemSpinnerBinding.bind(convertView)
        }

        if (list[position].code == "UZS") {
            Glide.with(parent.context)
                .load(R.drawable.flag_uzb)
                .into(binding.flagView)
        } else {
            Glide.with(parent.context)
                .load("https://nbu.uz/local/templates/nbu/images/flags/${list[position].code}.png")
                .into(binding.flagView)
        }

        binding.title.text = list[position].code

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView2: View? = convertView
        if (convertView == null) {
            convertView2 = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_spinner, parent, false)
        }
        val rowItem = getItem(position)
        val txtTitle =
            convertView2?.findViewById<View>(R.id.title) as TextView
        txtTitle.text = rowItem.code
        val imageView: ImageView =
            convertView2.findViewById<View>(R.id.flag_view) as ImageView

        if (list[position].code == "UZS") {
            Glide.with(parent?.context!!)
                .load(R.drawable.flag_uzb)
                .into(imageView)
        } else {
            Glide.with(parent?.context!!)
                .load("https://nbu.uz/local/templates/nbu/images/flags/${list[position].code}.png")
                .into(imageView)
        }
        return convertView2
    }
}