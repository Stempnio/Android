package pl.edu.uj.ecommerce.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.ecommerce.Activities.CartActivity
import pl.edu.uj.ecommerce.Cart
import pl.edu.uj.ecommerce.Product
import pl.edu.uj.ecommerce.Products
import pl.edu.uj.ecommerce.R

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>(){
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice : TextView = itemView.findViewById(R.id.textViewProductPrice)
        val buttonAddToCart : Button = itemView.findViewById(R.id.buttonAddToCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Products.products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.textViewProductName.text = Products.products[position].productName
        holder.textViewProductPrice.text = Products.products[position].productPrice.toString()

        holder.buttonAddToCart.setOnClickListener {
            val pName = holder.textViewProductName.text.toString()
            val pPrice = holder.textViewProductPrice.text.toString()
            Cart.productsInCart.add(Product(pName, pPrice.toDouble()))

            notifyItemInserted(position)

            // needed for displaying text view total price in real time
            notifyDataSetChanged()

            val intent = Intent(holder.buttonAddToCart.context, CartActivity::class.java)

            holder.buttonAddToCart.context.startActivity(intent)

        }
    }
}