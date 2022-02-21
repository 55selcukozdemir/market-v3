package com.egrikiraz.market.ui.stockstatus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.ProductM

class StockStatusViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text


    val db = ProductDatabaseDBHelper(getApplication<Application>().applicationContext)

    fun data (): MutableList<ProductM> {
        return db.productAllList()
    }
}