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
import pl.edu.uj.ecommerce.Products
import pl.edu.uj.ecommerce.R

class ProductsFragment : Fragment() {

    lateinit var button: Button
    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)

//                // TODO delete??
//                Products.getProductsFromDB()

                adapter = ProductTmpAdapter()
            }
        }
        return view
    }
}