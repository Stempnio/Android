package pl.edu.uj.ecommerce.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import pl.edu.uj.ecommerce.Data.CartItem
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.admin.functions.deleteAllCarts
import pl.edu.uj.ecommerce.admin.functions.deleteCustomerCart
import pl.edu.uj.ecommerce.admin.view_models.AdminCartViewModel
import pl.edu.uj.ecommerce.databinding.FragmentAdminCartBinding
import retrofit2.Call
import retrofit2.Response


class AdminCartFragment : Fragment() {
    private var _binding : FragmentAdminCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AdminCartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminCartBinding.inflate(layoutInflater, container, false)

        binding.btnAdminCartDeleteAll.setOnClickListener {
            deleteAllCarts()
        }

        binding.btnAdminCartDeleteCustomerCart.setOnClickListener {
            val id = binding.etAdminCartDeleteCustomerCart.text.toString()
            if(id != "")
                deleteCustomerCart(id)
        }

        binding.btnAdminCartRefresh.setOnClickListener {
            getAllCarts()
        }

        viewModel.cartListString.observe(viewLifecycleOwner, { string ->
            binding.tvAdminCartList.text = string
        })

        return binding.root
    }

    private fun getAllCarts() {
        val service = RetrofitService.create()
        val call = service.getAllCartItemsCall()
        call.enqueue(object : retrofit2.Callback<List<CartItem>> {
            override fun onResponse(
                call: Call<List<CartItem>>,
                response: Response<List<CartItem>>
            ) {
                val cartList = response.body()

                if(cartList != null) {
                    displayCart(cartList)
                } else {
                    val notFound = getString(R.string.not_found)
                    binding.tvAdminCartList.text = notFound
                }

                Log.d("GET_ALL_CARTS", "success")
            }

            override fun onFailure(call: Call<List<CartItem>>, t: Throwable) {
                binding.tvAdminCartList.text = t.message.toString()
            }


        })
    }

    fun displayCart(list : List<CartItem>) {
        CoroutineScope(Default).launch {
            var result = ""
            list.forEach { result += "customerID:" +
                    it.customerId + "| product:" +
                    it.productId + "| quantity:" +
                    it.quantity + "\n" }

            viewModel.cartListString.postValue(result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}