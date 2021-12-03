package uz.ismoilroziboyev.valute

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.ismoilroziboyev.valute.adapters.AllValutesRvAdapter
import uz.ismoilroziboyev.valute.databinding.ActivityMainBinding
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.viewModel.MyViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var allValutesRvAdapter: AllValutesRvAdapter
    private lateinit var myViewModel: MyViewModel
    private lateinit var listAllValutes: ArrayList<ValuteModel>
    private lateinit var listResult: ArrayList<ValuteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun loadList() {
        listAllValutes = ArrayList()
        listResult = ArrayList()
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        allValutesRvAdapter =
            AllValutesRvAdapter(object : AllValutesRvAdapter.OnItemClickListener {
                override fun onItemClickListener(item: ValuteModel) {
                    Toast.makeText(this@MainActivity, "Item clicked!", Toast.LENGTH_SHORT).show()
                }
            }, listResult)

        myViewModel.getValutesList(this).observe(this, Observer {
            listAllValutes.addAll(listAllValutes)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}