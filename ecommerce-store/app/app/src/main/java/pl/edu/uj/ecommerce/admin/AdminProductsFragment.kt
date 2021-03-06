package pl.edu.uj.ecommerce.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.edu.uj.ecommerce.Data.Product
import pl.edu.uj.ecommerce.R
import pl.edu.uj.ecommerce.RetrofitService
import pl.edu.uj.ecommerce.admin.functions.deleteAllProducts
import pl.edu.uj.ecommerce.admin.functions.deleteProductById
import pl.edu.uj.ecommerce.admin.functions.postProduct
import pl.edu.uj.ecommerce.admin.functions.updateProduct
import pl.edu.uj.ecommerce.databinding.FragmentAdminProductsBinding
import retrofit2.Response


class AdminProductsFragment : Fragment() {
    private var _binding : FragmentAdminProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProductsBinding.inflate(layoutInflater, container, false)

        addProductConf()

        updateProductConf()

        deleteProductsAllConf()

        deleteProductByIdConf()

        getProductByIdConf()

        return binding.root
    }

    private fun addProductConf() {
        binding.btnAdminProductsAdd.setOnClickListener {
            val name = binding.etAdminProductsAddName.text.toString()
            val price = binding.etAdminProductsAddPrice.text.toString()
            val description = binding.etAdminProductsAddDescription.text.toString()

            if(name != "" && price != "") {
                postProduct(Product().apply {
                    this.name = name
                    this.price = price.toInt()
                    this.description = description
                })
            }
        }
    }

    private fun updateProductConf() {
        binding.btnAdminProductsUpdate.setOnClickListener {
            val id = binding.etAdminProductsUpdateId.text.toString()
            val name = binding.etAdminProductsUpdateName.text.toString()
            val price = binding.etAdminProductsUpdatePrice.text.toString()
            val description = binding.etAdminProductsUpdateDescription.text.toString()

            if(id != "" && name != "" && price != "") {
                updateProduct(Product().apply {
                    this.id = id.toInt()
                    this.name = name
                    this.price = price.toInt()
                    this.description = description
                })
            }
        }
    }

    private fun deleteProductByIdConf() {
        binding.btnAdminProductsDeleteById.setOnClickListener {
            val id = binding.etAdminProductsDeleteById.text.toString().toInt()

            deleteProductById(id)
        }
    }

    private fun deleteProductsAllConf() {
        binding.btnAdminProductsDeleteAll.setOnClickListener {
            deleteAllProducts()
        }
    }


    private fun getProductByIdConf() {
        binding.btnAdminProductsGetById.setOnClickListener {

            val idString = binding.etAdminProductsGetById.text.toString()

            if(idString != "") {
                val id = idString.toInt()

                val service = RetrofitService.create()
                val call = service.getProductByIdCall(id)
                call.enqueue(object : retrofit2.Callback<Product> {
                    override fun onResponse(
                        call: retrofit2.Call<Product>,
                        response: Response<Product>
                    ) {
                        val prod = response.body()
                        if (prod != null) {
                            binding.tvAdminProductsGetId.text = prod.id.toString()
                            binding.tvAdminProductsGetName.text = prod.name
                            binding.tvAdminProductsGetPrice.text = prod.price.toString()
                            binding.tvAdminProductsGetDescription.text = prod.description
                        } else {
                            binding.tvAdminProductsGetId.text = "-1"
                            binding.tvAdminProductsGetName.text =
                                getString(R.string.product_not_found)
                            binding.tvAdminProductsGetPrice.text = ""
                            binding.tvAdminProductsGetDescription.text = ""
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<Product>, t: Throwable) {
                        Log.d("GET_PRODUCT_BY_ID", t.message.toString())
                    }

                })
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}