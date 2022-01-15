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
import pl.edu.uj.ecommerce.Data.Order
import pl.edu.uj.ecommerce.Data.OrderDetails
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.admin.functions.deleteAllOrders
import pl.edu.uj.ecommerce.admin.functions.deleteOrderById
import pl.edu.uj.ecommerce.admin.view_models.AdminOrderViewModel
import pl.edu.uj.ecommerce.databinding.FragmentAdminOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminOrderFragment : Fragment() {
    private var _binding : FragmentAdminOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AdminOrderViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminOrderBinding.inflate(layoutInflater, container, false)

        binding.btnAdminOrderRefresh.setOnClickListener {
            getAllOrders()
        }

        viewModel.orderListString.observe(viewLifecycleOwner, { string ->
            binding.tvAdminOrderList.text = string
        })

        viewModel.orderDetailsListString.observe(viewLifecycleOwner, { string ->
            binding.tvAdminOrderDetailsGetById.text = string
        })

        binding.btnAdminOrderDetailsGetById.setOnClickListener {
            val idString = binding.etAdminOrderDetailsGetById.text.toString()
            if(idString != "")
                getOrderDetailsById(idString.toInt())
        }

        binding.btnAdminOrderDeleteAll.setOnClickListener {
            deleteAllOrders()
        }

        binding.btnAdminOrderDeleteById.setOnClickListener {
            val idString = binding.etAdminOrderDeleteById.text.toString()
            if(idString != "")
                deleteOrderById(idString.toInt())
        }

        return binding.root
    }

    private fun getOrderDetailsById(id : Int) {
        val service = RetrofitService.create()
        val call = service.getOrderDetailsByIdCall(id)
        call.enqueue(object : Callback<List<OrderDetails>> {
            override fun onResponse(
                call: Call<List<OrderDetails>>,
                response: Response<List<OrderDetails>>
            ) {
                if(response.isSuccessful) {
                    val list = response.body()

                    if(list != null) {
                        displayOrderDetails(list)
                    } else {
                        binding.tvAdminOrderDetailsGetById.text = getString(R.string.not_found)
                    }
                    Log.d("GET_ORDER_DETAILS_BY_ID", response.message().toString())
                } else {
                    binding.tvAdminOrderDetailsGetById.text = getString(R.string.not_found)
                    Log.d("GET_ORDER_DETAILS_BY_ID", response.message().toString())
                }
            }

            override fun onFailure(call: Call<List<OrderDetails>>, t: Throwable) {
                Log.d("GET_ORDER_DETAILS_BY_ID", t.message.toString())
            }

        })
    }

    fun displayOrderDetails(list : List<OrderDetails>) {
        CoroutineScope(Default).launch {
            var result = ""

            list.forEach {
                result += "order ID: " + it.orderId +
                        " | product ID: " + it.productId +
                        " | quantity: " + it.quantity + "\n"

            }

            viewModel.orderDetailsListString.postValue(result)
        }
    }

    fun getAllOrders() {
        val service = RetrofitService.create()
        val call = service.getAllOrdersCall()
        call.enqueue(object : Callback<List<Order>>{
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if(response.isSuccessful) {
                    val list = response.body()

                    if(list != null) {
                        displayOrders(list)
                    }
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.d("GET_ALL_ORDERS", t.message.toString())
            }

        })
    }

    fun displayOrders(list : List<Order>) {
        CoroutineScope(Default).launch {
            var result = ""

            list.forEach {
                result += "customer ID: " + it.customerId +
                        " | date: " + it.date +
                        " | order ID: " + it.id + "\n"

            }

            viewModel.orderListString.postValue(result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}