package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.Customer
import pl.edu.uj.ecommerce.Data.postCustomer
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_products) {
    var _binding : FragmentRegisterBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnRegisterGoBack.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }

        binding.btnRegisterSignUp.setOnClickListener {
            val customerId = binding.editTextRegisterId.text.toString()
            val customerEmail = binding.editTextSetEmail.text.toString()
            val password = binding.editTextSetPassword.text.toString()
            val repeatPassword = binding.editTextRepeatPassword.text.toString()

            if(customerId == "")
                Toast.makeText(context, getString(R.string.enter_username), Toast.LENGTH_SHORT).show()
            else if(customerId.contains("admin"))
                Toast.makeText(context, "ID cannot contain 'admin' keyword", Toast.LENGTH_SHORT).show()
            else if(customerEmail == "")
                Toast.makeText(context, getString(R.string.enter_email), Toast.LENGTH_SHORT).show()
            else if(!customerEmail.contains('@'))
                Toast.makeText(context, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
            else if(password == "" || repeatPassword == "")
                Toast.makeText(context, getString(R.string.enter_passwords), Toast.LENGTH_SHORT).show()
            else if(password != repeatPassword)
                    Toast.makeText(context, getString(R.string.passwords_not_equal), Toast.LENGTH_SHORT).show()
            else {
                val newCustomer = Customer().apply {
                    this.id = customerId
                    this.email = customerEmail
                    this.password = password
                }
                postCustomer(newCustomer)

                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}