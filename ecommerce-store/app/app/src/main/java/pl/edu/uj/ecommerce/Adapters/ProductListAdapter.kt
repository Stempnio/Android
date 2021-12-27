package pl.edu.uj.ecommerce.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uj.ecommerce.Data.postCart
import pl.edu.uj.ecommerce.Data.Product
import pl.edu.uj.ecommerce.R

class ProductListAdapter(private val products : List<Product>) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice : TextView = itemView.findViewById(R.id.textViewProductPrice)
        val buttonAddToCart : Button = itemView.findViewById(R.id.buttonAddToCart)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.textViewProductName.text = products[position].name
        holder.textViewProductPrice.text = products[position].price.toString()
        holder.buttonAddToCart.setOnClickListener {
            // TODO check if it is correct
            notifyItemChanged(position)
            postCart(products[position].id)
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }


}