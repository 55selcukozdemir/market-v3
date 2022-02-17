package com.example.market2.ui.stockstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.market2.databinding.FragmentStockStatusBinding

class StockStatusFragment : Fragment() {

    private var _binding: FragmentStockStatusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
                ViewModelProvider(this).get(StockStatusViewModel::class.java)

        _binding = FragmentStockStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }


        val recylerView = binding.stockRecycler

        recylerView.layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL, false)
        recylerView.adapter = StockRecyclerAdapter()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}