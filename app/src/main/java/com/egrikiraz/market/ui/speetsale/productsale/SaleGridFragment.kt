package com.egrikiraz.market.ui.speetsale.productsale

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.ui.speetsale.SpeetSaleFragment


class SaleGridFragment (val productList: ArrayList<ProductM>, private val speetSaleFragment: SpeetSaleFragment) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sale_grid, container, false)


        val recylerView = view.findViewById<RecyclerView>(R.id.speet_sale_recyler)
        recylerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recylerView.adapter = RecyclerSpeetSaleAdapter(productList, this)

        return view
    }


    fun priceTextChange(text: String){
        speetSaleFragment.saleTextChange(text)
    }
    fun nameTextChane(text: String){
        speetSaleFragment.saleNameChange(text)
    }

    fun buttonDefauld(){
        speetSaleFragment.buttonDefauld()
    }

    fun barcodeTextClear(): EditText {
        return speetSaleFragment.saleBarcodeTextClear()
    }


}