package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.databinding.FragmentLogInBinding
import pl.edu.uj.ecommerce.Data.getProductsIntoDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInFragment : Fragment(R.layout.fragment_products) {

    private var _binding : FragmentLogInBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater, container, false)

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }

        binding.buttonLogIn.setOnClickListener {
            // TODO oauth2

            val id = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            logIn(id, password)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun logIn(id : String, password: String) {

        if(id == "" || password == "") {
            Toast.makeText(context, "ENTER USERNAME AND PASSWORD", Toast.LENGTH_LONG).show()
            return
        }

        val service = RetrofitService.create()
        val call = service.getCustomerByIdCall(id)

        call.enqueue(object : Callback<Customer> {
            override fun onResponse(
                call: Call<Customer>,
                response: Response<Customer>
            ) {
                if (response.code() == 200) {
                    val customer = response.body()

                    if(customer == null || customer.password != password) {
                        Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                        return
                    }

                    CURRENT_CUSTOMER_ID = id


                    if(!(CURRENT_CUSTOMER_ID.contains("admin"))) {
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
                        // update products and customers cart
                        getProductsIntoDB()
                        getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)

                        refreshCart()
                        refreshOrders()
                    } else {
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToAdminHomeFragment())
                    }
                } else {
                    Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                Log.d(resources.getString(R.string.log_in_failed), t.message.toString())
            }

        })
    }
}