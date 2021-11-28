package pl.edu.uj.ecommerce.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.ecommerce.Cart
import pl.edu.uj.ecommerce.Product
import pl.edu.uj.ecommerce.R

class ProductCartAdapter : RecyclerView.Adapter<ProductCartAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewProductNameCart)
        val textViewProductPrice : TextView = itemView.findViewById(R.id.textViewProductPriceCart)
        val buttonRmFromCart : Button = itemView.findViewById(R.id.buttonRmFromCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_product_row, parent, false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Cart.productsInCart.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.textViewProductName.text = Cart.productsInCart[position].productName
        holder.textViewProductPrice.text = Cart.productsInCart[position].productPrice.toString()

        holder.buttonRmFromCart.setOnClickListener {
            val pName = holder.textViewProductName.text.toString()
            val pPrice = holder.textViewProductPrice.text.toString()
            Cart.productsInCart.remove(Product(pName, pPrice.toDouble()))

            notifyItemRemoved(position)

            //// needed for displaying text view total price in real time
            notifyDataSetChanged()
        }
    }
}