package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import pl.edu.uj.ecommerce.Adapters.RecyclerViewOrderAdapter
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.OrderRealm
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentOrdersBinding


class OrdersFragment : Fragment(R.layout.fragment_orders) {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)

        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(context)
        val data = Realm.getDefaultInstance().where(OrderRealm::class.java)
            .equalTo("customerId", CURRENT_CUSTOMER_ID)
            .findAll()
        binding.recyclerViewOrders.adapter = RecyclerViewOrderAdapter(data)

        binding.btnOrdersGoBack.setOnClickListener {
            findNavController().navigate(OrdersFragmentDirections.actionOrdersFragmentToProductsFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}