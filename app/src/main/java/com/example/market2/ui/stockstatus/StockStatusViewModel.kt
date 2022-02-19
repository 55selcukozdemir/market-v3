package com.example.market2.ui.stockstatus

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market2.database.ProductDatabaseDBHelper
import com.example.market2.database.model.ProductM

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