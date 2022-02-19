package com.example.market2.ui.stockstatus

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.example.market2.R
import com.example.market2.database.ProductDatabaseDBHelper
import com.example.market2.database.model.ProductM
import com.github.dhaval2404.imagepicker.ImagePicker

import java.io.ByteArrayOutputStream

class ProductAddActivity : AppCompatActivity() {
    private val TAG = "ProductAddActivity"

    lateinit var spinner1: Spinner //Kategori
    lateinit var spinner2: Spinner //Birimler

    lateinit var imageView: ImageView //Ürün Görseli

    lateinit var name_t: EditText //Ürün Adı
    lateinit var barcod_t: EditText //Ürün Barkodu
    lateinit var sale_price_t: EditText //Satış Fiyatı
    lateinit var purchase_price_t: EditText //Alış Fiyatı
    lateinit var add_t: EditText //Ürün Adeti
    lateinit var add_product_t: EditText //Eklenen Ürün Adeti

    lateinit var scan_btn: Button  //Tarama Butonu
    lateinit var delete_btn: Button //Silme Butonu
    lateinit var add_btn: Button //Ekle Butonu
    lateinit var get_btn: Button //Getir Butonu
    lateinit var update_btn: Button //Güncelle Butonu

    lateinit var db: ProductDatabaseDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        //initialize - Başlatmak
        spinner1 = findViewById(R.id.a_category_s)
        spinner2 = findViewById(R.id.a_unit_s)

        imageView = findViewById(R.id.a_product_image)

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



        add_btn.setOnClickListener(View.OnClickListener {
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ona)

            val bitmap = imageView.drawable.toBitmap()
            val bos = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
            val img = bos.toByteArray()


            if (!name_t.text.isEmpty() ||
                !barcod_t.text.isEmpty() ||
                !sale_price_t.text.isEmpty() ||
                !purchase_price_t.text.isEmpty() ||
                !add_t.text.isEmpty() ||
                !add_product_t.text.isEmpty()
            ) {

                db.putProduct(
                    ProductM(
                        name_t.text.toString(),
                        barcod_t.text.toString(),
                        sale_price_t.text.toString().toDouble(),
                        purchase_price_t.text.toString().toDouble(),
                        add_product_t.text.toString().toInt(),
                        spinner1.selectedItem.toString(),
                        spinner2.selectedItem.toString(),
                        img
                    )
                )

            } else {
                Toast.makeText(this, "Boş kısımları doldurunuz.", Toast.LENGTH_LONG).show()
            }
        })

        get_btn.setOnClickListener(View.OnClickListener {
            val urunler = db.readProduct(barcod_t.text.toString())
            for (u in urunler) {
                name_t.setText(u.name, TextView.BufferType.EDITABLE)

                val bitmap = BitmapFactory.decodeByteArray(u.image, 0, u.image.size)
                imageView.setImageBitmap(bitmap)
            }
        })

        imageView.setOnClickListener(View.OnClickListener {
            dialog()
        })





        spinner()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK && requestCode== ImagePicker.REQUEST_CODE) {


            imageView?.setImageURI(data?.data)

        }
    }

    fun spinner() {
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

    fun dialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seçim yapınız")
        builder.setPositiveButton("Kamera", DialogInterface.OnClickListener { dialogInterface, i ->
            ImagePicker.with(this)
                .cameraOnly()
                .galleryMimeTypes(arrayOf("image/*"))
                .cropSquare()
                .maxResultSize(400, 400).start()
        })

        builder.setNegativeButton("Galeri", DialogInterface.OnClickListener { dialogInterface, i ->
            ImagePicker.with(this)
                .galleryOnly()
                .galleryMimeTypes(arrayOf("image/*"))
                .cropSquare()
                .maxResultSize(400, 400).start()
        })

        builder.show()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}