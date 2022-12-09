package com.overcom.bananaapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.overcom.bananaapp.domain.GetProduct
import com.overcom.bananaapp.domain.model.Products
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProduct: GetProduct
) : ViewModel() {

    constructor() : this(GetProduct())

    val _listProducts = MutableLiveData<List<Products>?>()
    var listProducts: LiveData<List<Products>?> = _listProducts

    val _activateFilter = MutableLiveData<Boolean>()
    var activateFilter: LiveData<Boolean> = _activateFilter

    private val _load = MutableLiveData<Boolean>()
    var load: LiveData<Boolean> = _load


    fun onCreate() {
        _load.value = true
        viewModelScope.launch {
            val result = getProduct()

            if (!result.isNullOrEmpty()) {
                _listProducts.postValue(result)
                _load.value = false
            }
        }
    }
}

