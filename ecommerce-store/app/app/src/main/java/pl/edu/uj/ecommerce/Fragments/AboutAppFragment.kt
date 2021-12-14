package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentAboutAppBinding
import pl.edu.uj.ecommerce.databinding.FragmentTestBinding

class AboutAppFragment : Fragment(R.layout.fragment_products) {

    private var _binding : FragmentAboutAppBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutAppBinding.inflate(layoutInflater, container, false)

        binding.buttonGoBack.setOnClickListener {
            findNavController().navigate(AboutAppFragmentDirections.actionAboutAppFragmentToProductsFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}