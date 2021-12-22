package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.carTotalPrice
import pl.edu.uj.ecommerce.Data.cartToString
import pl.edu.uj.ecommerce.Data.postOrder
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentBuyBinding

class BuyFragment : Fragment(R.layout.fragment_buy) {
    private var _binding: FragmentBuyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyBinding.inflate(layoutInflater, container, false)

        binding.tvBuyFragmentCartDetails.text = cartToString()

        binding.tvBuyFragmentTotalPrice.text = carTotalPrice().toString()

        binding.buttonBuyFragmentPlaceOrder.setOnClickListener {
            postOrder()
//            refreshCart()
            findNavController().navigate(BuyFragmentDirections.actionBuyFragmentToProductsFragment())
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}