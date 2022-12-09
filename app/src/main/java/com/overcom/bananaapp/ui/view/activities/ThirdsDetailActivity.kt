package com.overcom.bananaapp.ui.view.activities

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
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.overcom.bananaapp.BananaApp
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.ActivityThirdsDetailBinding
import com.overcom.bananaapp.ui.viewmodel.ThirdsDetailViewModel
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.CollectionAdapter

class ThirdsDetailActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityThirdsDetailBinding
    lateinit var viewModel: ThirdsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            this.let {
                ViewModelProvider(it)[ThirdsDetailViewModel::class.java]
            }

        binding = ActivityThirdsDetailBinding.inflate(layoutInflater)
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

        tabs()
    }

    fun tabs() {
        val collectionAdapter = CollectionAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager2)
        viewPager.adapter = collectionAdapter

        val tabs: TabLayout = findViewById(R.id.tabLayout)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getStringArray(R.array.thirds_details)[position]
        }.attach()
    }

   /* fun doThis(item: MenuItem) {
        val view = layoutInflater.inflate(R.layout.toast_error, null)
        val text = view.findViewById<TextView>(R.id.toastMessage_error)

        when (item.itemId) {
            R.id.Logout -> viewModel.logout(this, text, view, MainActivity::class.java)
        }
    }*/

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