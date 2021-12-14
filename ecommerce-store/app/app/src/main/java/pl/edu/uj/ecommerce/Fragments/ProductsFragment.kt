package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.ecommerce.Adapters.ProductTmpAdapter
import pl.edu.uj.ecommerce.Product
import pl.edu.uj.ecommerce.Products
import pl.edu.uj.ecommerce.Products.getProductsFromDbIntoList
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {

    //TODO viewbinding
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)

        val recyclerViewProductList = binding.RecycleViewFragmentProductListF
        recyclerViewProductList.layoutManager = LinearLayoutManager(context)

        //TODO view model
        val products = getProductsFromDbIntoList()
        recyclerViewProductList.adapter = ProductTmpAdapter(products)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}