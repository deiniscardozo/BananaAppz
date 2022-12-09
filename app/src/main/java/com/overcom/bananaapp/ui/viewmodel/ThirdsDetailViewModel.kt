package com.overcom.bananaapp.ui.viewmodel

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.lifecycle.*
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.data.model.Archivo
import com.overcom.bananaapp.data.model.DocumentsItem
import com.overcom.bananaapp.data.model.ThirdContact
import com.overcom.bananaapp.data.model.ThirdsDataDetailItem
import com.overcom.bananaapp.data.repositories.HomeRepository
import com.overcom.bananaapp.data.repositories.ThirdsDetailRepository
import kotlinx.coroutines.launch

class ThirdsDetailViewModel : ViewModel() {

    private val repository = ThirdsDetailRepository()
   // private val repositoryHome = HomeRepository()

    private val _thirdsDetail = MutableLiveData<ThirdsDataDetailItem?>()
    var thirdsDetail : MutableLiveData<ThirdsDataDetailItem?> = _thirdsDetail

    private val _listContact = MutableLiveData<List<ThirdContact>>()
    var listContact : MutableLiveData<List<ThirdContact>> = _listContact

    private val _listDocuments = MutableLiveData<List<DocumentsItem>>()
    var listDocuments : MutableLiveData<List<DocumentsItem>> = _listDocuments

    private val _listArchivos = MutableLiveData<List<Archivo>>()
    var listArchivos : MutableLiveData<List<Archivo>> = _listArchivos

    private val _name = MutableLiveData<String>()
    var name:  LiveData<String> = _name

    val _activateFilter = MutableLiveData<Boolean>()
    var activateFilter: LiveData<Boolean> = _activateFilter

    fun callThirdsDetail(context: Context, text: TextView, view: View){
        viewModelScope.launch {
            val call = repository.callThirdsDetail(preferences.getThirdsID())

            if (call.isSuccessful){

                call.body()!!.let { list->
                    _thirdsDetail.value = list
                    _listContact.value = list.third_contacts
                    _listDocuments.value = list.docvenc
                    _listArchivos.value = list.archivos
                }

            }else{
                Util.callNotSuccessful(context, call.errorBody()?.string().toString(), text, view)
            }
        }
    }

    /*fun logout(context: Context, text: TextView, view: View, activity: Class<*>) {
        viewModelScope.launch {
            val call = repositoryHome.logout()

            if(call.isSuccessful){
                preferences.logout()
                Util.finish()
                Util.intentActivity(context, activity)
            } else {
                Util.callNotSuccessful(context, call.errorBody()!!.string(),text, view)}
        }
    }*/

}