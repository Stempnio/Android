package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.getCurrentCustomer
import pl.edu.uj.ecommerce.Data.updateCustomer
import pl.edu.uj.ecommerce.databinding.FragmentUpdateCustomerBinding

class UpdateCustomerFragment : Fragment() {
    private var _binding : FragmentUpdateCustomerBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateCustomerBinding.inflate(layoutInflater, container, false)

        binding.buttonUpdatePassword.setOnClickListener {
            val password = binding.editTextUpdatePassword.text.toString()
            val repeatPassword = binding.editTextUpdatePasswordRepeat.text.toString()

            if(password != repeatPassword) {
                Toast.makeText(context, "Passwords are not equal!", Toast.LENGTH_SHORT).show()
            } else if(password == "" || repeatPassword == "") {
                Toast.makeText(context, "Enter your password!", Toast.LENGTH_SHORT).show()
            } else {
                val currentCustomer = getCurrentCustomer()
                currentCustomer.apply {
                    this.password = password
                }
                updateCustomer(currentCustomer)
                findNavController()
                    .navigate(UpdateCustomerFragmentDirections.actionUpdateCustomerFragmentToProductsFragment())
            }
        }

        binding.btnUpdateCustomerGoBack.setOnClickListener {
            findNavController().navigate(UpdateCustomerFragmentDirections.actionUpdateCustomerFragmentToProductsFragment())
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}