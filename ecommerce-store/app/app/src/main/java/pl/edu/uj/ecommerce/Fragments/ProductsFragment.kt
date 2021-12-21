package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.uj.ecommerce.Adapters.ProductListAdapter
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.DEFAULT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.getCartIntoDB
import pl.edu.uj.ecommerce.Products.getProductsFromDbIntoList
import pl.edu.uj.ecommerce.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {

    //TODO back stack handling
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)

        val recyclerViewProductList = binding.RecycleViewProductList
        recyclerViewProductList.layoutManager = LinearLayoutManager(context)

        //TODO view model
        val products = getProductsFromDbIntoList()
        recyclerViewProductList.adapter = ProductListAdapter(products)


        binding.buttonAboutApp.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToAboutAppFragment())
        }

        binding.buttonGoToCart.setOnClickListener {
            getCartIntoDB(CURRENT_CUSTOMER_ID)
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToCartFragment())
        }

        binding.buttonLogOut.setOnClickListener {
            CURRENT_CUSTOMER_ID = DEFAULT_CUSTOMER_ID
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToLogInFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}