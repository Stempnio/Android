package pl.edu.uj.zadanie_3.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.zadanie_3.Activities.CartActivity
import pl.edu.uj.zadanie_3.Cart
import pl.edu.uj.zadanie_3.Product
import pl.edu.uj.zadanie_3.Products
import pl.edu.uj.zadanie_3.R

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

            val intent = Intent(holder.buttonAddToCart.context, CartActivity::class.java)

            holder.buttonAddToCart.context.startActivity(intent)

        }
    }
}