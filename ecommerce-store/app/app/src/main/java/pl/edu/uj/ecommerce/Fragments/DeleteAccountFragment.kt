package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.deleteCustomer
import pl.edu.uj.ecommerce.databinding.FragmentDeleteAccountBinding

class DeleteAccountFragment : Fragment() {
    private var _binding : FragmentDeleteAccountBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteAccountBinding.inflate(layoutInflater, container, false)

        binding.buttonDeleteAccount.setOnClickListener {
            deleteCustomer()
            findNavController()
                .navigate(DeleteAccountFragmentDirections.actionDeleteAccountFragmentToLogInFragment())
        }

        binding.btnDeleteAccountGoBack.setOnClickListener {
            findNavController().navigate(DeleteAccountFragmentDirections.actionDeleteAccountFragmentToProductsFragment())
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}