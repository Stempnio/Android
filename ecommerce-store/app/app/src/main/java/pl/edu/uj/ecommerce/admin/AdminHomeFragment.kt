package pl.edu.uj.ecommerce.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.DEFAULT_CUSTOMER_ID
import pl.edu.uj.ecommerce.databinding.FragmentAboutAppBinding
import pl.edu.uj.ecommerce.databinding.FragmentAdminHomeBinding

class AdminHomeFragment : Fragment() {
    private var _binding : FragmentAdminHomeBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHomeBinding.inflate(layoutInflater, container, false)

        binding.btnAdminHomeCarts.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminCartFragment())
        }

        binding.btnAdminHomeCustomers.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminCustomerFragment())
        }

        binding.btnAdminHomeOrders.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminOrderFragment())
        }

        binding.btnAdminHomeProducts.setOnClickListener {
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminProductsFragment())
        }

        binding.btnAdminHomeLogOut.setOnClickListener {
            CURRENT_CUSTOMER_ID = DEFAULT_CUSTOMER_ID
            findNavController().navigate(AdminHomeFragmentDirections.actionAdminHomeFragmentToLogInFragment())
        }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}