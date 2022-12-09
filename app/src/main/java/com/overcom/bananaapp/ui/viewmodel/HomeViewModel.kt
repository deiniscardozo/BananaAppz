package com.overcom.bananaapp.ui.viewmodel

import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.core.Util.finish
import com.overcom.bananaapp.data.model.ThirdsData
import com.overcom.bananaapp.data.repositories.HomeRepository
import com.overcom.bananaapp.data.repositories.ThirdsRepository
import com.overcom.bananaapp.ui.view.fragmentes.home.NavFragment
import com.overcom.bananaapp.ui.view.fragmentes.thirds.adapter.ThirdsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryHome: HomeRepository,
    private val repositoryThirds: ThirdsRepository
) : ViewModel() {

    constructor() : this(HomeRepository(), ThirdsRepository())

    private var mAdapter: ThirdsAdapter? = null

    private val _org = MutableLiveData<Map<String, Int>>()
    var org: LiveData<Map<String, Int>> = _org

    private val _orgID = MutableLiveData<Int>()

    private val _orgText = MutableLiveData<String>()
    var orgText: LiveData<String> = _orgText

    val _listThirds = MutableLiveData<List<ThirdsData>>()
    var listThirds: LiveData<List<ThirdsData>> = _listThirds

    private val _type_third = MutableLiveData<String>()
    var type_third: LiveData<String> = _type_third

    val _position = MutableLiveData<Int>()
    var position: LiveData<Int> = _position

    private val _limit = MutableLiveData<Int>()
    var limit: LiveData<Int> = _limit

    private val _load = MutableLiveData<Boolean>()
    var load: LiveData<Boolean> = _load

    val _activateFilter = MutableLiveData<Boolean>()
    var activateFilter: LiveData<Boolean> = _activateFilter

    val _filter = MutableLiveData<String>()
    var filter: LiveData<String> = _filter
    fun orgSelector(
        manager: FragmentManager,
        context: Context,
        text: TextView,
        view: View,
        orgView: View
    ) {

        viewModelScope.launch {
            val call = repositoryHome.orgSelector()
            val error = repositoryHome.orgErrorSelector()
            _org.value = call?.organizations?.associate { Pair(it.text, it.id) }

            if (call?.organizations?.isNotEmpty() == true) {
                if (call.organizations.count() <= 1) {
                    if (call.organizations.isEmpty().toString() == "true") {

                        Handler().postDelayed({
                            Util.callNotSuccessful(
                                context, "Usted no tiene Organización asignada, " +
                                        "por favor comuníquese con un administrador.", text, view
                            )
                        }, 4000)
                    }

                    _orgID.value = org.value?.values?.first()
                    _orgText.value = org.value?.keys?.first()

                } else {
                    dialogOrganization(manager, context, orgView)
                }
            } else {
                Util.callNotSuccessful(context, error?.string(), text, view)
            }
        }
    }

    private fun dialogOrganization(manager: FragmentManager, context: Context, orgView: View) {
        if (preferences.getFirstRun()) {
            val builder = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            val spinnerOrg = orgView.findViewById<Spinner>(R.id.listOrganizations)

            builder.setView(orgView).setIcon(R.drawable.logo_banana)
                .setTitle(
                    Html.fromHtml("<font color='#000000'><b>Seleccionar Organización</b></font>")
                )
                .setPositiveButton(
                    R.string.OK,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                        Util.intentFragment(
                            NavFragment(), manager,
                            R.id.nav_host_fragment_content_home
                        )
                    })

            viewModelScope.launch {
                if (orgView.parent != null) {
                    (orgView.parent as ViewGroup).removeView(orgView)
                }

                builder.show()
                preferences.saveFirstRun(false)
            }

            val list = ArrayList(org.value!!.keys)
            val aaOrganizations = ArrayAdapter(
                context, android.R.layout.simple_dropdown_item_1line,
                list
            )

            spinnerOrg.adapter = aaOrganizations
            spinnerOrg.setSelection(0)
            spinnerOrg.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    preferences.saveOrgId(org.value!![list[position]].toString())
                    preferences.saveOrgText(list[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    fun thirdsCall(typeThirds: String, context: Context, text: TextView, view: View) {
        _type_third.value = typeThirds
        _limit.value = 100
        _load.value = true
        _filter.value = filter.value ?: ""

        viewModelScope.launch {

            val call = repositoryThirds.thirdsCall(
                type_third.value.toString(), filter.value.toString(),
                limit.value.toString(), position.value.toString()
            )
            val error = repositoryThirds.thirdsErrorCall(
                type_third.value.toString(), filter.value.toString(),
                limit.value.toString(), position.value.toString()
            )

            if (call.isNotEmpty()) {
                call.let { list ->

                    if (listThirds.value.isNullOrEmpty()) {
                        _listThirds.value = list
                    } else {
                        if (filter.value.isNullOrEmpty() && !list.contains(listThirds.value?.last()))
                            _listThirds.value = listThirds.value?.plus(list)
                    }

                    _load.value = false
                    _limit.value = list.size
                }

            } else {
                Util.callNotSuccessful(context, error?.string().toString(), text, view)
            }
        }
    }

    fun filterThirds(context: Context, text: TextView, view: View) {
        val builder = AlertDialog.Builder(context, R.style.CustomDialogTheme)
        val filterThirds = R.array.thirds_filter

        builder.setIcon(R.drawable.logo_banana)
            .setTitle(R.string.choose_an_option)
            .setSingleChoiceItems(filterThirds, -1,
                DialogInterface.OnClickListener { dialog, which ->

                    dialog.cancel()

                    if (which == 0) {
                        _type_third.value = "customer"
                    } else {
                        if (which == 2) {
                            _type_third.value = "prospect"
                        } else {
                            if (which == 1) {
                                _type_third.value = "customer_prospect"
                            } else {
                                if (which == 3) {
                                    _type_third.value = "customer_prospect_archivados"
                                } else {
                                    if (which == 4)
                                        _type_third.value = "customer_prospect"
                                }
                            }
                        }
                    }
                    _listThirds.value = listOf()
                    thirdsCall(type_third.value.toString(), context, text, view)
                })

        builder.create()
        builder.show()
    }

    fun logout(context: Context, text: TextView, view: View, activity: Class<*>) {
        viewModelScope.launch {
            repositoryHome.logout()
            preferences.logout()
            finish()
            Util.intentActivity(context, activity)
        }
    }
}