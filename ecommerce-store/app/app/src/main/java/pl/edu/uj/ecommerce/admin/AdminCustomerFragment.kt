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
import pl.edu.uj.ecommerce.Data.Customer
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.admin.functions.deleteAllCustomers
import pl.edu.uj.ecommerce.admin.functions.deleteCustomerById
import pl.edu.uj.ecommerce.admin.view_models.AdminCustomerViewModel
import pl.edu.uj.ecommerce.databinding.FragmentAdminCustomerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminCustomerFragment : Fragment() {
    private var _binding : FragmentAdminCustomerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AdminCustomerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminCustomerBinding.inflate(layoutInflater, container, false)

        binding.btnAdminCustomerDeleteAll.setOnClickListener {
            deleteAllCustomers()
        }

        binding.btnAdminCustomerRefresh.setOnClickListener {
            getAllCustomers()
        }

        binding.btnAdminCustomerDeleteById.setOnClickListener {
            val id = binding.etAdminCustomerDeleteById.text.toString()
            if(id != "")
                deleteCustomerById(id)
        }

        binding.btnAdminCustomerGetById.setOnClickListener {
            val id = binding.etAdminCustomerGetById.text.toString()
            if(id != "")
                getCustomerById(id)
        }

        viewModel.customerListString.observe(viewLifecycleOwner, {string ->
            binding.tvAdminCustomerList.text = string
        })


        return binding.root
    }

    private fun getCustomerById(id : String) {
        val service = RetrofitService.create()
        val call = service.getCustomerByIdCall(id)
        call.enqueue(object : Callback<Customer> {
            override fun onResponse(
                call: Call<Customer>,
                response: Response<Customer>
            ) {
                if (response.code() == 200) {
                    val cust = response.body()
                    if(cust != null) {
                        binding.tvAdminCustomerGetId.text = cust.id
                        binding.tvAdminCustomerGetFirst.text = cust.firstName
                        binding.tvAdminCustomerGetLastName.text = cust.lastName
                        binding.tvAdminCustomerGetEmail.text = cust.email
                    }
                    Log.d("GET_CUSTOMER_BY_ID", "success")
                } else {
                    binding.tvAdminCustomerGetId.text = getString(R.string.customer_not_found_id)
                    binding.tvAdminCustomerGetFirst.text = getString(R.string.customer_not_found)
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                binding.tvAdminCustomerGetId.text = getString(R.string.customer_not_found_id)
                binding.tvAdminCustomerGetFirst.text = getString(R.string.customer_not_found)
                Log.d("GET_CUSTOMER_BY_ID", t.message.toString())
            }

        })
    }

    private fun getAllCustomers() {
        val service = RetrofitService.create()
        val call = service.getAllCustomersCall()
        call.enqueue(object : Callback<List<Customer>>{
            override fun onResponse(
                call: Call<List<Customer>>,
                response: Response<List<Customer>>
            ) {
                val customerList = response.body()

                if(customerList != null) {
                    displayCustomers(customerList)
                } else {
                    binding.tvAdminCustomerList.text = getString(R.string.not_found)
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                Log.d("GET_CUSTOMERS_ALL", t.message.toString())
            }

        })
    }

    fun displayCustomers(list : List<Customer>) {
        CoroutineScope(Default).launch {
            var result = ""
            list.forEach {
                result += "customerID:" +
                        it.id + " | first name:" +
                        it.firstName + " | last name:" +
                        it.lastName + " | email:" +
                        it.email + "\n"
            }

            viewModel.customerListString.postValue(result)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}