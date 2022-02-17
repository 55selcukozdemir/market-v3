package com.example.market2.ui.speetsale.productsale

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market2.R


class SaleGridFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sale_grid, container, false)


        val recylerView = view.findViewById<RecyclerView>(R.id.speet_sale_recyler)
        recylerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recylerView.adapter = RecyclerSpeetSaleAdapter()

        return view
    }


}