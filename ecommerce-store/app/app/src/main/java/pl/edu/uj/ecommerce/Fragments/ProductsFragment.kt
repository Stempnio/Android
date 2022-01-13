package pl.edu.uj.ecommerce.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.uj.ecommerce.Adapters.ProductListAdapter
import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.databinding.FragmentProductsBinding

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater, container, false)

        val recyclerViewProductList = binding.RecycleViewProductList
        recyclerViewProductList.layoutManager = LinearLayoutManager(context)

        val products = getProductsFromRealmIntoList()
        recyclerViewProductList.adapter = ProductListAdapter(products)



        binding.buttonGoToCart.setOnClickListener {
            getCartIntoRealm()
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToCartFragment())
        }

        binding.buttonLogOut.setOnClickListener {
            CURRENT_CUSTOMER_ID = DEFAULT_CUSTOMER_ID
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToLogInFragment())
        }

        binding.buttonOrders.setOnClickListener {
            getOrdersIntoDB()
            getOrderDetailsIntoRealm()
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToOrdersFragment())
        }


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.updateCustomerFragment -> {
                findNavController()
                    .navigate(ProductsFragmentDirections.actionProductsFragmentToUpdateCustomerFragment())
                true
            }

            R.id.aboutAppFragment -> {
                findNavController()
                    .navigate(ProductsFragmentDirections.actionProductsFragmentToAboutAppFragment())
                true
            }

            R.id.deleteAccountFragment -> {
                findNavController()
                    .navigate(ProductsFragmentDirections.actionProductsFragmentToDeleteAccountFragment())
                true
            }
            R.id.googleMapsFragment -> {
                findNavController()
                    .navigate(ProductsFragmentDirections.actionProductsFragmentToGoogleMapsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}