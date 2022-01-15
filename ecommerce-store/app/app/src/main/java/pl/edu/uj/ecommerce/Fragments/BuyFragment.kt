package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Source
import com.stripe.android.model.SourceParams
import pl.edu.uj.ecommerce.Data.carTotalPrice
import pl.edu.uj.ecommerce.Data.cartToString
import pl.edu.uj.ecommerce.Data.postOrder
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentBuyBinding

class BuyFragment : Fragment(R.layout.fragment_buy) {
    private var _binding: FragmentBuyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyBinding.inflate(layoutInflater, container, false)

//        setTestCard()

        binding.tvBuyFragmentCartDetails.text = cartToString()

        binding.tvBuyFragmentTotalPrice.text = carTotalPrice().toString()

        binding.buttonBuyFragmentPlaceOrder.setOnClickListener {

            val stripe = Stripe(requireContext(),getString(R.string.stripe_api_key))
            val cardParams = binding.cardInputWidget.cardParams
            if(cardParams != null) {
                val cardSourceParams = SourceParams.createCardParams(cardParams)

                stripe.createSource(cardSourceParams, callback = object : ApiResultCallback<Source> {
                    override fun onError(e: Exception) {
                        Log.e("STRIPE_DEBUG", e.message.toString())
                        Toast.makeText(requireContext(), "ERROR WHILE VALIDATING CARD", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess(result: Source) {
                        Log.d("STRIPE_DEBUG", "success")
                        postOrder()
                        findNavController().navigate(BuyFragmentDirections.actionBuyFragmentToProductsFragment())
                    }
                })
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setTestCard() {
        binding.cardInputWidget.setCardNumber("4242424242424242")
        binding.cardInputWidget.setCvcCode("123")
        binding.cardInputWidget.setExpiryDate(1,24)
    }
}