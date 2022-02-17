package com.example.market2.ui.speetsale.bottom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.market2.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * A simple [Fragment] subclass.
 * Use the [SaleBottomSheet.newInstance] factory method to
 * create an instance of this fragment.
 */
class SaleBottomSheet : BottomSheetDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sale_bottom_sheet, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager  = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL,false)

        recyclerView.adapter = RecyclerBottomAdapter(arrayOf(
            "özddsemirsd",
            "özdedsmir",
            "özdedsmir",
            "özdemdsir",
            "özdedsmir",
            "özdedsmir",
            "özdfhgemir",
            ))

        return view
    }



}