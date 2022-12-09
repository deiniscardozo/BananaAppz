package com.overcom.bananaapp.ui.view.activities

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.overcom.bananaapp.BananaApp
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.ActivityMapsBinding
import com.overcom.bananaapp.ui.viewmodel.MapsViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var viewModel: MapsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            this.let {
                ViewModelProvider(it)[MapsViewModel::class.java]
            }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel._activateFilter.value = false

        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val viewDrawer = navView.getHeaderView(0)
        val avatar = viewDrawer.findViewById<ImageView>(R.id.avatarView)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_thirds,
                R.id.Logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewDrawer.findViewById<TextView>(R.id.UsersView).text = BananaApp.preferences.getUserName()
        viewDrawer.findViewById<TextView>(R.id.orgView).text = BananaApp.preferences.getOrgText()
        viewDrawer.findViewById<TextView>(R.id.worksapaceView).text =
            BananaApp.preferences.getWorkspace()
        Glide.with(avatar.context).load(BananaApp.preferences.getUserImage())
            .placeholder(R.drawable.ic_thirds).into(avatar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        val direction = intent.extras?.getString("values")
        val customer = intent.extras?.getString("values1")
        val result: List<Address> =
            direction?.let { Geocoder(this).getFromLocationName(it, 1) } as List<Address>

        mMap = googleMap

        if (result.isEmpty()) {
            val snackbar = Snackbar.make(
                binding.root, "Dirección No Válida",
                Snackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.BLUE)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.BLACK)
            val textView =
                snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            textView.textSize = 14f
            snackbar.show()
            //Util.callNotSuccessful("Dirección No Válida")

        } else {
            val address = LatLng(result[0].latitude, result[0].longitude)

            mMap.addMarker(MarkerOptions().position(address).title(customer.toString()))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(address))
            mMap.setMaxZoomPreference(20F)
            mMap.setMinZoomPreference(15F)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}