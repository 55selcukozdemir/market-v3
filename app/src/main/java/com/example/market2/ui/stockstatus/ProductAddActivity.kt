package com.example.market2.ui.stockstatus

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    lateinit var add_tv: TextView //Ürün Adeti
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

        //----------------------------------------------------------------------------------
        //initialize - Başlatmak
        spinner1 = findViewById(R.id.a_category_s)
        spinner2 = findViewById(R.id.a_unit_s)

        imageView = findViewById(R.id.a_product_image)

        name_t = findViewById(R.id.a_name_tv)
        barcod_t = findViewById(R.id.a_barcode_tv)
        sale_price_t = findViewById(R.id.a_saleprice_tv)
        purchase_price_t = findViewById(R.id.a_purchaseprice_tv)
        add_tv = findViewById(R.id.a_add_et)
        add_product_t = findViewById(R.id.a_add_product_tv)

        scan_btn = findViewById(R.id.sales_scan_btn)
        delete_btn = findViewById(R.id.a_delete_btn)
        add_btn = findViewById(R.id.a_add_btn)
        get_btn = findViewById(R.id.a_get_btn)
        update_btn = findViewById(R.id.a_update_btn)
        //----------------------------------------------------------------------------------
        spinner()

        //Ürün düzenlerken recycler view dan gelen barkoddan sqliteden verilerin tamamını çekiyoruz.
        db = ProductDatabaseDBHelper(this)
        startedDB()


        //Butonların işlevlerinin atandığı kısımdır.
        add_btn.setOnClickListener(View.OnClickListener { add() })

        get_btn.setOnClickListener(View.OnClickListener { get() })

        delete_btn.setOnClickListener(View.OnClickListener { delete() })

        update_btn.setOnClickListener(View.OnClickListener { update() })

        imageView.setOnClickListener(View.OnClickListener { dialog() })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE) {
            imageView?.setImageURI(data?.data)
        }
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
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


    fun delete() {
        db.prodctDelete(barcod_t.text.toString())
        startedDB()
    }

    fun get() {
        val urunler = db.readProduct(barcod_t.text.toString())
        for (u in urunler) {
            name_t.setText(u.name, TextView.BufferType.EDITABLE)
            barcod_t.setText(u.barcode)
            sale_price_t.setText(u.sale_price.toString())
            purchase_price_t.setText(u.purchase_price.toString())
            add_tv.setText(u.number_of_products.toString())
            val bitmap = BitmapFactory.decodeByteArray(u.image, 0, u.image.size)
            imageView.setImageBitmap(bitmap)

            val category = resources.getStringArray(R.array.array_catecory)
            var cInt = 0
            for (c in category) {
                if (c == u.category) {
                    spinner1.setSelection(cInt)
                }
                cInt++
            }

            val untils = resources.getStringArray(R.array.array_units)
            var uInt = 0
            for (un in untils) {
                if (un == u.unit) {
                    spinner2.setSelection(uInt)
                }
                uInt++
            }
        }
    }

    fun add() {
        val bitmap = imageView.drawable.toBitmap()
        val bos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
        val img = bos.toByteArray()

        if (!name_t.text.isEmpty() &&
            !barcod_t.text.isEmpty() &&
            !sale_price_t.text.isEmpty() &&
            !purchase_price_t.text.isEmpty() &&
            !add_product_t.text.isEmpty()
        ) {


            val existing = db.readProduct(barcod_t.text.toString())

            if (existing.isEmpty()) {
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
                for (existings in existing) {
                    if (!existings.barcode.equals(barcod_t.text.toString())) {
                        db.putProduct(
                            ProductM(
                                name_t.text.toString(),
                                barcod_t.text.toString(),
                                sale_price_t.text.toString().toDouble(),
                                purchase_price_t.text.toString().toDouble(),
                                add_product_t.text.toString()
                                    .toInt() + existings.number_of_products,
                                spinner1.selectedItem.toString(),
                                spinner2.selectedItem.toString(),
                                img
                            )
                        )
                    } else {
                        Toast.makeText(
                            this,
                            "Bu ürün barkoduyla depoda mevcut. Lütfen güncelleme yaparak ürünün özelliklerini değiştiriniz.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Boş kısımları doldurunuz.", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun update() {

        val bitmap = imageView.drawable.toBitmap()
        val bos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
        val img = bos.toByteArray()

        if (!name_t.text.isEmpty() &&
            !barcod_t.text.isEmpty() &&
            !sale_price_t.text.isEmpty() &&
            !purchase_price_t.text.isEmpty() &&
            !add_product_t.text.isEmpty()
        ) {
            val existing = db.readProduct(barcod_t.text.toString())

            if (existing.isEmpty()) {
                Toast.makeText(this, "Böyle bir ürün yoktur", Toast.LENGTH_SHORT).show()
            } else {
                for (existings in existing) {
                    if (existings.barcode.equals(barcod_t.text.toString())) {
                        db.productUpdate(
                            ProductM(
                                name_t.text.toString(),
                                barcod_t.text.toString(),
                                sale_price_t.text.toString().toDouble(),
                                purchase_price_t.text.toString().toDouble(),
                                add_product_t.text.toString().toInt() + existings.number_of_products,
                                spinner1.selectedItem.toString(),
                                spinner2.selectedItem.toString(),
                                img
                            )
                        )
                        startedDB()
                    }
                }
            }
        } else {
            Toast.makeText(applicationContext, "Boş kısımları doldurunuz.", Toast.LENGTH_LONG).show()
        }


    }


    fun startedDB() {
        val masterBarcoed = intent.getStringExtra("barcode")
        if (masterBarcoed != null) {
            val list = db.readProduct(masterBarcoed)

            for (l in list) {
                name_t.setText(l.name)
                barcod_t.setText(l.barcode)
                sale_price_t.setText(l.sale_price.toString())
                purchase_price_t.setText(l.purchase_price.toString())
                add_tv.setText(l.number_of_products.toString())
                val bitmap = BitmapFactory.decodeByteArray(l.image, 0, l.image.size)
                imageView.setImageBitmap(bitmap)


                val category = resources.getStringArray(R.array.array_catecory)
                var cInt = 0
                for (c in category) {
                    if (c == l.category) {
                        spinner1.setSelection(cInt)
                    }
                    cInt++
                }


                val untils = resources.getStringArray(R.array.array_units)
                var uInt = 0
                for (u in untils) {
                    if (u == l.unit) {
                        spinner2.setSelection(uInt)
                    }
                    uInt++
                }

            }
        }
    }

    fun dialog() {
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

}
