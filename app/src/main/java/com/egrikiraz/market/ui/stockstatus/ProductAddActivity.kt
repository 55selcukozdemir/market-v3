package com.egrikiraz.market.ui.stockstatus

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.egrikiraz.market.R
import com.egrikiraz.market.ScannerActivity
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.ProductEntry
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.ui.speetsale.buttonClick
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage

import java.io.ByteArrayOutputStream
import java.util.*

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
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
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

        storage = Firebase.storage
        firestore = Firebase.firestore

        //Ürün düzenlerken recycler view dan gelen barkoddan sqliteden verilerin tamamını çekiyoruz.
        db = ProductDatabaseDBHelper(this)
        startedDB()


        //Butonların işlevlerinin atandığı kısımdır.
        add_btn.setOnClickListener(View.OnClickListener { fireAdd() })

        get_btn.setOnClickListener(View.OnClickListener { get() })

        delete_btn.setOnClickListener(View.OnClickListener { delete() })

        update_btn.setOnClickListener(View.OnClickListener { update() })

        imageView.setOnClickListener(View.OnClickListener { dialog() })

        scan_btn.setOnClickListener(View.OnClickListener {
            val i = Intent(this, ScannerActivity::class.java)
            startActivity(i)
        })


        if (intent.getStringExtra("newAdd").equals("newAdd")) {
            delete_btn.visibility = View.INVISIBLE
            get_btn.visibility = View.INVISIBLE
            update_btn.visibility = View.INVISIBLE

        } else {
            add_btn.visibility = View.INVISIBLE
        }


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

    override fun onStart() {
        super.onStart()
        if (buttonClick.barcode != "") {
            barcod_t.setText(buttonClick.barcode)
            buttonClick.barcode = ""
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


    fun delete() {
        if (db.readProduct(barcod_t.text.toString()).isEmpty()) {
            Toast.makeText(this, "Bu barkoda sahip ürün yoktur ve silinemez.", Toast.LENGTH_SHORT)
                .show()
        } else {
            db.prodctDelete(barcod_t.text.toString())
            onBackPressed()
        }

    }

    fun get() {
        val urunler = db.readProduct(barcod_t.text.toString())

        if (urunler.isEmpty()) {
            Toast.makeText(this, "Bu barkoda sahip bir ürün yokur.", Toast.LENGTH_SHORT).show()
        } else {
            for (u in urunler) {
                name_t.setText(u.name, TextView.BufferType.EDITABLE)
                barcod_t.setText(u.barcode)
                sale_price_t.setText(u.sale_price.toString())
                purchase_price_t.setText(u.purchase_price.toString())
                add_tv.text = "Ürün Adedi : " + u.number_of_products.toString()
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

    }


// şuanlık kullanılmıyor
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

                onBackPressed()
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
                        onBackPressed()
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
                Toast.makeText(this, "Bu barkoda sahip ürün yoktur.", Toast.LENGTH_SHORT).show()
            } else {
                for (existings in existing) {
                    if (existings.barcode.equals(barcod_t.text.toString())) {
                        db.productUpdate(
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
                    }
                }
                onBackPressed()
            }
        } else {
            Toast.makeText(applicationContext, "Boş kısımları doldurunuz.", Toast.LENGTH_LONG)
                .show()
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
                add_tv.setText("Ürün Adedi : " + l.number_of_products.toString())
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

    fun fireAdd() {

        if (!name_t.text.isEmpty() &&
            !barcod_t.text.isEmpty() &&
            !sale_price_t.text.isEmpty() &&
            !purchase_price_t.text.isEmpty() &&
            !add_product_t.text.isEmpty()
        ) {

            val storageRef = FirebaseStorage.getInstance().reference

            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val dataq = baos.toByteArray()

            storageRef.child("${UUID.randomUUID()}.jpg").putBytes(dataq).addOnSuccessListener { taskSnapshot ->

            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = "task.result"
                    val data = hashMapOf(
                        ProductEntry.PRODUCT_NAME to name_t.text.toString(),
                        ProductEntry.BARCODE to barcod_t.text.toString(),
                        ProductEntry.SALE_PRICE to sale_price_t.text.toString(),
                        ProductEntry.PURCHASE_PRICE to purchase_price_t.text.toString(),
                        ProductEntry.NUMBER_OF_PRODUCTS to add_product_t.text.toString(),
                        ProductEntry.CATEGORY to spinner1.selectedItem.toString(),
                        ProductEntry.UNIT to spinner2.selectedItem.toString(),
                        "url" to downloadUri
                    )
                    firestore.collection("product").add(data).addOnSuccessListener {
                        Toast.makeText(
                            applicationContext,
                            "Sunucuya başarıyla gönderildi ${downloadUri}",
                            Toast.LENGTH_SHORT
                        ).show()

                        onBackPressed()
                    }




                } else {
                    // Handle failures
                    // ...
                }
            }

        } else {
            Toast.makeText(applicationContext, "Boş kısımları doldurunuz.", Toast.LENGTH_LONG)
                .show()
        }


    }

}
