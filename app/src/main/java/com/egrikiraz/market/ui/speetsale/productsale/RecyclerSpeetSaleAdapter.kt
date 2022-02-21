package com.egrikiraz.market.ui.speetsale.productsale

import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.database.model.SaleM
import com.egrikiraz.market.ui.speetsale.buttonClick

class RecyclerSpeetSaleAdapter(val productDataList: ArrayList<ProductM>, private val saleGridFragment: SaleGridFragment)  : RecyclerView.Adapter<RecyclerSpeetSaleAdapter.ViewHolder>(){

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val imageView = view.findViewById<ImageView>(R.id.speed_r_image)
        val name = view.findViewById<TextView>(R.id.speed_r_name)
        val price = view.findViewById<TextView>(R.id.speed_r_price)
        val cardView = view.findViewById<CardView>(R.id.speet_sale_g_r_cardview)
        val context = view.context




        fun bind(product : ProductM){
            val bitmap = BitmapFactory.decodeByteArray(product.image,0 , product.image.size)
            imageView.setImageBitmap(bitmap)
            name.text = product.name
            price.text = product.sale_price.toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSpeetSaleAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.speet_sale_recycler,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerSpeetSaleAdapter.ViewHolder, position: Int) {
        holder.bind(productDataList.get(position))


        holder.cardView.setOnClickListener(View.OnClickListener {
            val db = ProductDatabaseDBHelper(holder.context)

            if (db.readSale(productDataList.get(position).barcode).isEmpty()){
                db.putSale(
                    SaleM(
                        productDataList.get(position).name,
                        productDataList.get(position).barcode,
                        productDataList.get(position).sale_price,
                        productDataList.get(position).purchase_price,
                        productDataList.get(position).number_of_products,
                        productDataList.get(position).category,
                        productDataList.get(position).unit,
                        productDataList.get(position).image,
                        buttonClick.btnStatus
                    )
                )
                var totalPrice = 0.0
                for (saleList in db.saleAllList()){
                    totalPrice += saleList.sale_price * saleList.size
                }
                saleGridFragment.nameTextChane(productDataList.get(position).name)
                saleGridFragment.priceTextChange(totalPrice.toString())
                saleGridFragment.buttonDefauld()
                saleGridFragment.barcodeTextClear()


            } else {
                db.saleUpdate(
                    SaleM(
                        productDataList.get(position).name,
                        productDataList.get(position).barcode,
                        productDataList.get(position).sale_price,
                        productDataList.get(position).purchase_price,
                        productDataList.get(position).number_of_products,
                        productDataList.get(position).category,
                        productDataList.get(position).unit,
                        productDataList.get(position).image,
                        db.readSale(productDataList.get(position).barcode).get(0).size + buttonClick.btnStatus
                    )
                )
                var totalPrice = 0.0
                for (saleList in db.saleAllList()){
                    totalPrice += saleList.sale_price * saleList.size
                }

                saleGridFragment.priceTextChange(totalPrice.toString())
                saleGridFragment.nameTextChane(productDataList.get(position).name)
                saleGridFragment.buttonDefauld()
                saleGridFragment.barcodeTextClear()

            }



            val toneG = ToneGenerator(AudioManager.ADJUST_LOWER, 200)
            fun beep(duration: Int)
            {
                toneG.startTone(ToneGenerator.TONE_PROP_BEEP, duration)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    toneG.release()
                }, (duration + 50).toLong())
            }
            beep(100)
        })
    }

    override fun getItemCount(): Int {
        return productDataList.size
    }

}