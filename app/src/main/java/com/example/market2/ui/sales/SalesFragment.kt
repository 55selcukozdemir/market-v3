package com.example.market2.ui.sales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.market2.databinding.FragmentSalesBinding

class SalesFragment : Fragment() {

    private var _binding: FragmentSalesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
                ViewModelProvider(this).get(SalesViewModel::class.java)

        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root



        //val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }

        val recycleradapter = binding.salesRecycler
        recycleradapter.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        recycleradapter.adapter = SalesRecyclerAdapter()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}