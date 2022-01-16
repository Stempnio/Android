package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import kotlinx.coroutines.launch
import pl.edu.uj.ecommerce.Data.CURRENT_CUSTOMER_ID
import pl.edu.uj.ecommerce.Data.carTotalPrice
import pl.edu.uj.ecommerce.Data.cartToString
import pl.edu.uj.ecommerce.Data.postOrder
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.databinding.FragmentBuyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BuyFragment : Fragment(R.layout.fragment_buy) {
    private var _binding: FragmentBuyBinding? = null
    private val binding get() = _binding!!

    private lateinit var paymentIntentClientSecret: String
    private var gotClientSecret = false

    private lateinit var paymentLauncher: PaymentLauncher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PaymentConfiguration.init(requireContext(), getString(R.string.stripe_api_key))

        getClientSecret()

        val paymentConfiguration = PaymentConfiguration.getInstance(requireContext())
        paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyBinding.inflate(layoutInflater, container, false)

//        setTestCard()

        binding.tvBuyFragmentCartDetails.text = cartToString()

        binding.tvBuyFragmentTotalPrice.text = carTotalPrice().toString()

        binding.buttonBuyFragmentPlaceOrder.setOnClickListener {
            if(gotClientSecret) {
                binding.cardInputWidget.paymentMethodCreateParams?.let { params ->
                    val confirmParams = ConfirmPaymentIntentParams
                        .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)

                    lifecycleScope.launch {
                        paymentLauncher.confirm(confirmParams)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Error retrieving payment info from server!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {
        when (paymentResult) {
            is PaymentResult.Completed -> {
                postOrder()
                findNavController().navigate(BuyFragmentDirections.actionBuyFragmentToProductsFragment())
                Toast.makeText(requireContext(), "Order placed successfully!", Toast.LENGTH_SHORT).show()
            }
            is PaymentResult.Canceled -> {
                Toast.makeText(requireContext(), "Payment canceled!", Toast.LENGTH_SHORT).show()
            }
            is PaymentResult.Failed -> {
                Toast.makeText(requireContext(), paymentResult.throwable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getClientSecret() {
        RetrofitService
            .create()
            .createPaymentIntentCall(CURRENT_CUSTOMER_ID)
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if(response.isSuccessful && response.body() != null) {
                        paymentIntentClientSecret = response.body()!!
                        gotClientSecret = true
                    } else {
                        Log.e("stripe_response_fail", response.message())
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("stripe", t.message.toString())
                }

            })
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