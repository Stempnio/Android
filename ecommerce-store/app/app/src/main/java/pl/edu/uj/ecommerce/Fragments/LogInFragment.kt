package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.Customer
import pl.edu.uj.ecommerce.Data.getCartIntoDB
import pl.edu.uj.ecommerce.Data.getCustomerByIdIntoDB
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
            // TODO logging system (for example oauth)

            val id = binding.editTextTextEmail.text.toString()
            var password = binding.editTextPassword.text.toString()

            logIn(id)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun logIn(id : String) {
        val service = RetrofitService.create()
        val call = service.getCustomerByIdCall(id)

        call.enqueue(object : Callback<Customer> {
            override fun onResponse(
                call: Call<Customer>,
                response: Response<Customer>
            ) {
                if (response.code() == 200) {
                    CURRENT_CUSTOMER_ID = id

                    // update products and customers cart
                    getProductsIntoDB()
                    getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)
                    getCartIntoDB(CURRENT_CUSTOMER_ID)

                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
                }

            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(context, "LOG IN FAILED", Toast.LENGTH_LONG).show()
            }

        })
    }
}