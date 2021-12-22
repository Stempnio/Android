package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
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
import pl.edu.uj.ecommerce.getProductsIntoDB
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

            val id = binding.editTextTextEmail.text.toString()
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

        if(id == "") {
            Toast.makeText(context, "LOG IN FAILED", Toast.LENGTH_LONG).show()
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
                        Toast.makeText(context, "LOG IN FAILED", Toast.LENGTH_LONG).show()
                        return
                    }

                    CURRENT_CUSTOMER_ID = id

                    // update products and customers cart
                    getProductsIntoDB()
                    getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)

                    refreshCart()
                    getOrdersIntoDB()
                    getOrderDetailsIntoDB()

                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
                }

            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(context, "LOG IN FAILED", Toast.LENGTH_LONG).show()
            }

        })
    }
}