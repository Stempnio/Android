package pl.edu.uj.ecommerce.Fragments

import RecyclerViewCartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
//import pl.edu.uj.ecommerce.Adapters.CartListAdapter
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.CartItemRealm
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        val recyclerViewCartList = binding.RecyclerViewCartList
        recyclerViewCartList.layoutManager = LinearLayoutManager(context)

        val adapter = RecyclerViewCartAdapter(Realm.getDefaultInstance()
            .where(CartItemRealm::class.java)
            .equalTo("customerId", CURRENT_CUSTOMER_ID)
            .findAll())

        recyclerViewCartList.adapter = adapter


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