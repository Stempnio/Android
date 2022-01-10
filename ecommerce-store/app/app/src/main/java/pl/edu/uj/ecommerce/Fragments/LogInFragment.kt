package pl.edu.uj.ecommerce.Fragments

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException

import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.admin.Admin
import pl.edu.uj.ecommerce.databinding.FragmentLogInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInFragment : Fragment(R.layout.fragment_products) {

    private var _binding : FragmentLogInBinding? = null
    private val binding get() = _binding!!

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(layoutInflater, container, false)

        setUpGoogleClient()

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }

        binding.buttonLogIn.setOnClickListener {
            val id = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
            logIn(id, password)
        }

        binding.buttonLogInWithGoogle.setOnClickListener {
            googleLogIn()
        }

        return binding.root
    }


    private fun setUpGoogleClient() {
        oneTapClient = Identity.getSignInClient(requireActivity())
        oneTapClient.signOut()

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.serverId))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
    }

    private fun googleLogIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("TAG", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d("TAG", e.localizedMessage)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val googleCustomer = Customer().apply { this.id =
                        if(credential.id.length <= 20)
                            credential.id
                        else
                            credential.id.substring(0,19)}
                    googlePostCustomerIfNotExists(googleCustomer)


                } catch (e: ApiException) {
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logIn(id : String, password: String) {

        if(id == "" || password == "") {
            Toast.makeText(context, "ENTER USERNAME AND PASSWORD", Toast.LENGTH_LONG).show()
            return
        }

        if(id.contains("admin")) {
            adminLogIn(id, password)
        } else {
            customerLogIn(id, password)
        }

    }

    private fun googlePostCustomerIfNotExists(customer: Customer) {
        val service = RetrofitService.create()
        val call = service.getCustomerByIdCall(customer.id)
        call.enqueue(object : Callback<Customer> {
            override fun onResponse(
                call: Call<Customer>,
                response: Response<Customer>
            ) {
                if (response.code() != 200) {
                    postCustomer(customer)
                }

                CURRENT_CUSTOMER_ID = customer.id

                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
                // update products and customers cart
                getProductsIntoDB()
                getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)

                refreshCart()
                refreshOrders()

            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Log.d("GET CUSTOMER INTO DB FAILED", t.message.toString())
            }

        })
    }

    private fun customerLogIn(id : String, password: String) {
        val service = RetrofitService.create()
        val call = service.getCustomerByIdCall(id)

        call.enqueue(object : Callback<Customer> {
            override fun onResponse(
                call: Call<Customer>,
                response: Response<Customer>
            ) {
                if (response.code() == 200) {
                    val customer = response.body()

                    if(customer == null || customer.password != password) {
                        Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                        return
                    }

                    CURRENT_CUSTOMER_ID = id

                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductsFragment())
                    // update products and customers cart
                    getProductsIntoDB()
                    getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)

                    refreshCart()
                    refreshOrders()
                } else {
                    Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                Log.d(resources.getString(R.string.log_in_failed), t.message.toString())
            }

        })
    }

    private fun adminLogIn(id : String, password: String) {
        val service = RetrofitService.create()
        val call = service.getAdminByIdCall(id)

        call.enqueue(object : Callback<Admin> {
            override fun onResponse(call: Call<Admin>, response: Response<Admin>) {
                if (response.code() == 200) {
                    val admin = response.body()

                    if(admin == null || admin.password != password) {
                        Toast.makeText(context, response.message().toString(), Toast.LENGTH_LONG).show()
                        return
                    }

                    CURRENT_CUSTOMER_ID = id
                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToAdminHomeFragment())
                } else {
                    Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Admin>, t: Throwable) {
                Toast.makeText(context, resources.getString(R.string.log_in_failed), Toast.LENGTH_LONG).show()
                Log.d(resources.getString(R.string.log_in_failed), t.message.toString())
            }

        })
    }


}