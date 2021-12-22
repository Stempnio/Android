package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import pl.edu.uj.ecommerce.Data.orderDetailsToString
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {
    private var _binding: FragmentOrderDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsBinding.inflate(layoutInflater, container, false)


        val bookId = arguments?.getString("orderId")?.toInt()

        if(bookId != null) {
            val headerText = "DETAILS OF ORDER: $bookId"
            binding.tvOrderDetailsHeader.text = headerText
            binding.tvOrderDetails.text = orderDetailsToString(bookId)
        } else
            Toast.makeText(context, "ERROR, ORDER WITH ID: $bookId NOT FOUND", Toast.LENGTH_SHORT).show()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}