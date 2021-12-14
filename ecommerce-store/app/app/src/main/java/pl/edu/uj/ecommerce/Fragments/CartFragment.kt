package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_products) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        //TODO recyclerview cart
//        val recyclerViewCartList = binding.RecyclerViewCartList
//        recyclerViewCartList.layoutManager = LinearLayoutManager(context)
//
//        val products = Products.getProductsFromDbIntoList()
//        recyclerViewCartList.adapter = ProductCartAdapter(products)


        binding.buttonBuy.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToBuyFragment())
        }

        binding.buttonGoBack3.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToProductsFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}