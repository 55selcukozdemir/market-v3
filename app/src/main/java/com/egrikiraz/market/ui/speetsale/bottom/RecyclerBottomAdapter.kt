package com.egrikiraz.market.ui.speetsale.bottom

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.SaleM

class RecyclerBottomAdapter(private var dataSet: MutableList<SaleM>, private val saleBottomSheet: SaleBottomSheet ) :
    RecyclerView.Adapter<RecyclerBottomAdapter.ViewHolder>() {

    private lateinit var saleList: MutableList<SaleM>


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.bottom_r_image)
        val name = view.findViewById<TextView>(R.id.bottom_r_name)
        val cound = view.findViewById<TextView>(R.id.bottom_r_cound)
        val price = view.findViewById<TextView>(R.id.bottom_r_price)
        val more = view.findViewById<ImageView>(R.id.bottom_r_more)
        val less = view.findViewById<ImageView>(R.id.bottom_r_less)
        val context = view.context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_bottom_sheet_recycler, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bitmap = BitmapFactory.decodeByteArray(dataSet.get(position).image, 0, dataSet.get(position).image.size)
        holder.image.setImageBitmap(bitmap)
        holder.name.text = dataSet.get(position).name
        holder.cound.text = dataSet.get(position).size.toString()
        holder.price.text = dataSet.get(position).sale_price.toString()

        holder.less.setOnClickListener {
            val db = ProductDatabaseDBHelper(holder.context)
            for (saleList in db.readSale(dataSet.get(position).barcode)){
                if (holder.cound.text.toString().toInt() > 1){
                    db.saleUpdate(
                        SaleM(
                            dataSet.get(position).name,
                            dataSet.get(position).barcode,
                            dataSet.get(position).sale_price,
                            dataSet.get(position).purchase_price,
                            dataSet.get(position).number_of_products,
                            dataSet.get(position).category,
                            dataSet.get(position).unit,
                            dataSet.get(position).image,
                            dataSet.get(position).size - 1
                        )
                    )
                } else {
                    db.saleDelete(dataSet.get(position).barcode)
                }

            }

            var totalPrice = 0.0
            for (saleList in db.saleAllList()){
                totalPrice += saleList.sale_price * saleList.size
            }


            saleBottomSheet.salePriceChange(totalPrice.toString())
            dataSet.clear()
            dataSet = db.saleAllList()

            if (dataSet.isEmpty()){
                saleBottomSheet.saleNameChange("Sepet Boş")
            }

            this.notifyDataSetChanged()

        }

        holder.more.setOnClickListener {
            val db = ProductDatabaseDBHelper(holder.context)
            for (saleList in db.readSale(dataSet.get(position).barcode)){
                db.saleUpdate(
                    SaleM(
                        dataSet.get(position).name,
                        dataSet.get(position).barcode,
                        dataSet.get(position).sale_price,
                        dataSet.get(position).purchase_price,
                        dataSet.get(position).number_of_products,
                        dataSet.get(position).category,
                        dataSet.get(position).unit,
                        dataSet.get(position).image,
                        dataSet.get(position).size + 1

                    )
                )
            }

            var totalPrice = 0.0
            for (saleList in db.saleAllList()){
                totalPrice += saleList.sale_price * saleList.size
            }

            saleBottomSheet.salePriceChange(totalPrice.toString())

            dataSet.clear()
            dataSet = db.saleAllList()
            this.notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}