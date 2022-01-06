package pl.edu.uj.ecommerce.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.uj.ecommerce.Data.CartItem
import pl.edu.uj.ecommerce.Data.Product
import pl.edu.uj.ecommerce.Data.deleteAllCarts
import pl.edu.uj.ecommerce.Data.deleteCustomerCart
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.databinding.FragmentAboutAppBinding
import pl.edu.uj.ecommerce.databinding.FragmentAdminCartBinding
import retrofit2.Call
import retrofit2.Response


class AdminCartFragment : Fragment() {
    private var _binding : FragmentAdminCartBinding? = null

    private val binding get() = _binding!!


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
            deleteCustomerCart(id)
        }

        binding.btnAdminCartRefresh.setOnClickListener {
            getAllCarts()
        }

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
                    binding.tvAdminCartList.text = cartToString(cartList)
                } else {
                    binding.tvAdminCartList.text = "not found"
                }

                Log.d("GET_ALL_CARTS", "success")
            }

            override fun onFailure(call: Call<List<CartItem>>, t: Throwable) {
                binding.tvAdminCartList.text = t.message.toString()
            }


        })
    }

    fun cartToString( list : List<CartItem>) : String {
        var result = ""
        list.forEach { result += "customerID:" +
                it.customerId + "| product:" +
                it.productId + "| quantity:" +
                it.quantity + "\n" }
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}