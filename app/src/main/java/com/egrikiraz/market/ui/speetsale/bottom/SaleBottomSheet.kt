package com.egrikiraz.market.ui.speetsale.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.ui.speetsale.SpeetSaleFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * A simple [Fragment] subclass.
 * Use the [SaleBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaleBottomSheet(val speetSaleFragment: SpeetSaleFragment) : BottomSheetDialogFragment(){

    lateinit var saleButton : Button
    lateinit var price : TextView
    lateinit var db: ProductDatabaseDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sale_bottom_sheet, container, false)
        saleButton = view.findViewById(R.id.bottom_sale_btn)
        price = view.findViewById(R.id.bottom_sale_price_text)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager  = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL,false)

        db = ProductDatabaseDBHelper(context)

        var totalPrice = 0.0
        for (saleList in db.saleAllList()){
            totalPrice += saleList.sale_price * saleList.size
        }
        price.text = totalPrice.toString()

        recyclerView.adapter = RecyclerBottomAdapter(db.saleAllList(),this)

        saleButton.setOnClickListener {
            this.dismiss()





            db.deleteSale()

        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    fun salePriceChange(text: String){
        price.text = text
        speetSaleFragment.saleTextChange(text)
    }
    fun saleNameChange(text: String){
        speetSaleFragment.saleNameChange(text)
    }
}