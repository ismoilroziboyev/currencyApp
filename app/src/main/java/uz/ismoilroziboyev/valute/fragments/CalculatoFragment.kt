package uz.ismoilroziboyev.valute.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.adapters.SpinnerAdapter
import uz.ismoilroziboyev.valute.databinding.FragmentCalculatoBinding
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.utils.MySharedPreferences
import uz.ismoilroziboyev.valute.viewModel.MyViewModel
import java.text.DecimalFormat

class CalculatoFragment : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var binding: FragmentCalculatoBinding
    private lateinit var mySharedPreferences: MySharedPreferences
    private lateinit var myViewModel: MyViewModel
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: ArrayList<ValuteModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatoBinding.inflate(inflater, container, false)

        mySharedPreferences = MySharedPreferences.getInstance(requireContext())
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        list = ArrayList(listOf(ValuteModel("1", "UZS", "", "1", "1", "O'zbekiston so'mi")))


        binding.apply {

            spinnerAdapter = SpinnerAdapter(requireContext(), list)

            spinner1.adapter = spinnerAdapter
            spinner2.adapter = spinnerAdapter

            myViewModel.getValutesList(requireContext()).observe(viewLifecycleOwner, Observer {
                list.addAll(it)
                spinnerAdapter.notifyDataSetChanged()

                val currentCurrency = mySharedPreferences.getCurrentCalcCurrency()

                val index = list.indexOf(ValuteModel("", currentCurrency, "", "", "", ""))

                spinner1.setSelection(index)

            })

            spinner1.onItemSelectedListener = this@CalculatoFragment
            spinner2.onItemSelectedListener = this@CalculatoFragment

            changeSpinners.setOnClickListener {
                val valutePosition1 = spinner1.selectedItemPosition
                val valutePosition2 = spinner2.selectedItemPosition

                spinner1.setSelection(valutePosition2)
                spinner2.setSelection(valutePosition1)
            }

            editText1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    submitCalculating()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        }



        return binding.root
    }


    override fun onResume() {
        val currentCurrency = mySharedPreferences.getCurrentCalcCurrency()

        val index = list.indexOf(ValuteModel("", currentCurrency, "", "", "", ""))

        binding.spinner1.setSelection(index)
        super.onResume()
    }

    private fun submitCalculating() {
        val df = DecimalFormat("#.##")
        binding.apply {
            val valute1 = list[spinner1.selectedItemPosition]
            val valute2 = list[spinner2.selectedItemPosition]

            if (editText1.text.toString() == "") {
                cellPriceTv.text = "00.00  ${valute2.code}"
                buyPriceTv.text = "00.00  ${valute2.code}"
            } else {
                if (valute1.nbu_cell_price == "" && valute2.nbu_cell_price == "") {
                    val difference = valute1.cb_price.toDouble() / valute2.cb_price.toDouble()

                    val value = editText1.text.toString().toDouble() * difference

                    cellPriceTv.text = "${df.format(value)} ${valute2.code}"
                    buyPriceTv.text = "${df.format(value)} ${valute2.code}"

                } else if (valute1.nbu_cell_price != "" && valute2.nbu_cell_price != "") {
                    val difference1 =
                        valute1.nbu_cell_price.toDouble() / valute2.nbu_cell_price.toDouble()
                    val difference2 =
                        valute1.nbu_buy_price.toDouble() / valute2.nbu_buy_price.toDouble()

                    val valueCell = editText1.text.toString().toDouble() * difference1
                    val valueBuy = editText1.text.toString().toDouble() * difference2

                    cellPriceTv.text = "${df.format(valueCell)} ${valute2.code}"
                    buyPriceTv.text = "${df.format(valueBuy)} ${valute2.code}"
                } else {

                    if (valute1.nbu_cell_price == "") {
                        val difference1 =
                            valute1.cb_price.toDouble() / valute2.nbu_cell_price.toDouble()
                        val difference2 =
                            valute1.cb_price.toDouble() / valute2.nbu_buy_price.toDouble()

                        val valueCell = editText1.text.toString().toDouble() * difference1
                        val valueBuy = editText1.text.toString().toDouble() * difference2

                        cellPriceTv.text = "${df.format(valueCell)} ${valute2.code}"
                        buyPriceTv.text = "${df.format(valueBuy)} ${valute2.code}"
                    } else {
                        val difference1 =
                            valute1.nbu_cell_price.toDouble() / valute2.cb_price.toDouble()
                        val difference2 =
                            valute1.nbu_buy_price.toDouble() / valute2.cb_price.toDouble()

                        val valueCell = editText1.text.toString().toDouble() * difference1
                        val valueBuy = editText1.text.toString().toDouble() * difference2

                        cellPriceTv.text = "${df.format(valueCell)} ${valute2.code}"
                        buyPriceTv.text = "${df.format(valueBuy)} ${valute2.code}"
                    }
                }
            }

        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        submitCalculating()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}