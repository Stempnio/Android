package pl.edu.uj.ecommerce.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentAboutAppBinding
import pl.edu.uj.ecommerce.databinding.FragmentAdminProductsBinding
import pl.edu.uj.ecommerce.databinding.FragmentProductsBinding


class AdminProductsFragment : Fragment() {
    private var _binding : FragmentAdminProductsBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProductsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}