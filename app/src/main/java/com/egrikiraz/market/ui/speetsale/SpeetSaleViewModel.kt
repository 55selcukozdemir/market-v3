package com.egrikiraz.market.ui.speetsale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.ProductM

class SpeetSaleViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _textt = MutableLiveData<Int>().apply {
        value = buttonClick.btnStatus
    }
    val texty: LiveData<Int> = _textt


    val db = ProductDatabaseDBHelper(application.applicationContext)

    fun getProduct(): MutableList<ProductM> {
        return db.productAllList()
    }
}