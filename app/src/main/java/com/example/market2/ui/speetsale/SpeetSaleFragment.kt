package com.example.market2.ui.speetsale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.market2.adapter.CollectionPagerAdapter
import com.example.market2.databinding.FragmentSpeetSaleBinding
import com.example.market2.ui.speetsale.bottom.SaleBottomSheet
import com.example.market2.ui.speetsale.productsale.SaleGridFragment

class SpeetSaleFragment : Fragment() {



    private lateinit var CollectionPagerAdapter: CollectionPagerAdapter
    private lateinit var viewPager: ViewPager



    private var _binding: FragmentSpeetSaleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(SpeetSaleViewModel::class.java)

        _binding = FragmentSpeetSaleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }

        val newFragment1 = Fragment()

        val viewPager = binding.viewpager
        val tapBar = binding.tabLayout

        tapBar.setupWithViewPager(viewPager)

        val mFragment = CollectionPagerAdapter(childFragmentManager)

        mFragment.addFrag(SaleGridFragment(), "22")
        mFragment.addFrag(SaleGridFragment(), "33")
        mFragment.addFrag(SaleGridFragment(),"44")

        viewPager.adapter = mFragment



        binding.basket.setOnClickListener(View.OnClickListener {
            val saleBottomSheet = SaleBottomSheet()
            saleBottomSheet.show(parentFragmentManager,"blabla")
        })



        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}