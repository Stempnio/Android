package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentLogInBinding
import pl.edu.uj.ecommerce.databinding.FragmentTestBinding

class LogInFragment : Fragment(R.layout.fragment_products) {

    private var _binding : FragmentLogInBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater, container, false)

        binding.buttonLogIn.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}