package com.example.market2.ui.stockstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.market2.R

class ProductAddActivity : AppCompatActivity() {

    lateinit var spinner1: Spinner //Kategori
    lateinit var spinner2: Spinner //Birimler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)

        spinner()
    }

    fun spinner(){
        ArrayAdapter.createFromResource(
            this,
            R.array.array_catecory,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.array_units,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }
    }
}