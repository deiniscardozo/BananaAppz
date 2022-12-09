package com.overcom.bananaapp.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.R
import com.overcom.bananaapp.data.model.ThirdsData
import com.overcom.bananaapp.databinding.ActivityHomeBinding
import com.overcom.bananaapp.ui.view.fragmentes.thirds.adapter.ThirdsAdapter
import com.overcom.bananaapp.ui.viewmodel.HomeViewModel
import com.overcom.bananaapp.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.lowercase
import java.util.*
import java.util.Locale.filter

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private var filter: String? = null
    private lateinit var view: View
    private lateinit var text: TextView
    private val viewModelHome: HomeViewModel by viewModels()
    private val viewModelProducts: ProductsViewModel by viewModels()
    private var searchlisthirds: List<ThirdsData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelHome._activateFilter.value = false
        viewModelProducts._activateFilter.value = false

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val viewDrawer = navView.getHeaderView(0)
        val avatar = viewDrawer.findViewById<ImageView>(R.id.avatarView)

        view = layoutInflater.inflate(R.layout.toast_error, null)
        text = view.findViewById(R.id.toastMessage_error)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        this.appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_thirds,
                R.id.Logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewDrawer.findViewById<TextView>(R.id.UsersView).text = preferences.getUserName()
        viewDrawer.findViewById<TextView>(R.id.orgView).text = preferences.getOrgText()
        viewDrawer.findViewById<TextView>(R.id.worksapaceView).text = preferences.getWorkspace()
        Glide.with(avatar.context).load(preferences.getUserImage())
            .placeholder(R.drawable.ic_thirds).into(avatar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)

        val menuItem = menu.findItem(R.id.svThirds)
        val searchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               /* val thirdsFiltered =
                    viewModelHome.listThirds.value?.filter { thirds ->
                        thirds.name.lowercase().contains(query.lowercase())
                    }
                viewModelHome.search(thirdsFiltered as MutableList<ThirdsData>)*/

               val thirds =  viewModelHome.listThirds.observe(this@HomeActivity) {
                    searchlisthirds = it
                }

                val prueba = searchlisthirds?.filter { it ->
                    it.name.lowercase().contains(query.lowercase())
                }


                Log.i("DeinislupaT", prueba.toString())

                //viewModelHome._listThirds.value = listOf()
                //viewModelHome._listThirds.value = prueba
                Log.i("Deinisquery", query.toString().lowercase())
                
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               viewModelHome.listThirds.observe(this@HomeActivity) {
                    searchlisthirds = it
                }

                val thirds = searchlisthirds?.filter { it ->
                    it.name.lowercase().contains(newText.toString().lowercase())
                }

                // viewModelHome._listThirds.value = thirds

                Log.i("Deinisthi", thirds.toString())
                Log.i("Deinispro", newText.toString())


               /* val products = viewModelProducts.onCreate()

                viewModelHome._listThirds.value = thirds


                viewModelHome.filter.observe(this@HomeActivity) {
                    filter = it
                }

                viewModelHome._listThirds.value = thirds
                viewModelProducts._listProducts.value = listOf()

                if (!filter.equals(newText)) {
                    viewModelHome._filter.value = newText
                    if (thirds!!.equals(null)) {
                        products
                    } else {
                        thirds
                    }
                }*/
                return true
            }
        })
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.change_organization).isVisible = true

        viewModelHome.activateFilter.observe(this) { show ->
            menu.findItem(R.id.fvThirds).isVisible = show
            menu.findItem(R.id.svThirds).isVisible = show
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun doThis(item: MenuItem) {
        when (item.itemId) {
            R.id.Logout -> viewModelHome.logout(this, text, view, MainActivity::class.java)
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}