package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.databinding.ShrProductGridFragmentBinding
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter

class ProductGridFragment : Fragment() {
    private lateinit var binding: ShrProductGridFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShrProductGridFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar)
        with(binding) {
//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
//            val adapter =
//                ProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
//            recyclerView.adapter = adapter
//            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing)
//            val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small)
//            recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))

            // Set up the RecyclerView
            recyclerView.setHasFixedSize(true)
            val gridLayoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 3 == 2) 2 else 1
                }
            }
            recyclerView.layoutManager = gridLayoutManager
            val adapter = StaggeredProductCardRecyclerViewAdapter(
                ProductEntry.initProductEntryList(resources))
            recyclerView.adapter = adapter
            val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
            val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)
            recyclerView.addItemDecoration(ProductGridItemDecoration(largePadding, smallPadding))
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
