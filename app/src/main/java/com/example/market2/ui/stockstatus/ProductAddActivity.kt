package com.example.market2.ui.stockstatus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.market2.R
import com.example.market2.database.ProductDatabaseDBHelper
import com.example.market2.database.model.ProductM
import java.io.BufferedReader

class ProductAddActivity : AppCompatActivity() {

    lateinit var spinner1: Spinner //Kategori
    lateinit var spinner2: Spinner //Birimler

    lateinit var name_t: EditText //Ürün Adı
    lateinit var barcod_t: EditText //Ürün Barkodu
    lateinit var sale_price_t: EditText //Satış Fiyatı
    lateinit var purchase_price_t: EditText //Alış Fiyatı
    lateinit var add_t: EditText //Ürün Adeti
    lateinit var add_product_t : EditText //Eklenen Ürün Adeti

    lateinit var scan_btn: Button  //Tarama Butonu
    lateinit var delete_btn:Button //Silme Butonu
    lateinit var add_btn: Button //Ekle Butonu
    lateinit var get_btn : Button //Getir Butonu
    lateinit var update_btn : Button //Güncelle Butonu







    lateinit var db: ProductDatabaseDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        //initialize
        spinner1 = findViewById(R.id.a_category_s)
        spinner2 = findViewById(R.id.a_unit_s)


        name_t = findViewById(R.id.a_name_tv)
        barcod_t = findViewById(R.id.a_barcode_tv)
        sale_price_t = findViewById(R.id.a_saleprice_tv)
        purchase_price_t = findViewById(R.id.a_purchaseprice_tv)
        add_t = findViewById(R.id.a_add_tv)
        add_product_t = findViewById(R.id.a_add_product_tv)

        scan_btn = findViewById(R.id.sales_scan_btn)
        delete_btn = findViewById(R.id.a_delete_btn)
        add_btn = findViewById(R.id.a_add_btn)
        get_btn = findViewById(R.id.a_get_btn)
        update_btn = findViewById(R.id.a_update_btn)



        db = ProductDatabaseDBHelper(this)


        scan_btn.setOnClickListener(View.OnClickListener {
            db.putProduct(
                ProductM("nsndfdsjn", "sndkjnf", 1.2F, 13.3F, 2,5,"mskf", "dmksfks", "ndfjks")
            )
        })

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

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}