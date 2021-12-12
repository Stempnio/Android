package pl.edu.uj.ecommerce.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import pl.edu.uj.ecommerce.ProductRealm
import pl.edu.uj.ecommerce.Products
import pl.edu.uj.ecommerce.R

class ProductTmpAdapter : RecyclerView.Adapter<ProductTmpAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewProductName)
        val textViewProductPrice : TextView = itemView.findViewById(R.id.textViewProductPrice)
        val buttonAddToCart : Button = itemView.findViewById(R.id.buttonAddToCart)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_products, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0)
            return

        val product = Realm.getDefaultInstance().where(ProductRealm::class.java)
            .equalTo("id", position).findFirst()

        if (product != null) {
            holder.textViewProductName.text = product.name
            holder.textViewProductPrice.text = product.price.toString()
        } else {
            holder.textViewProductName.text = "null"
            holder.textViewProductPrice.text = "null"
        }

//        holder.textViewProductName.text = Products.products[position].name
//        holder.textViewProductPrice.text = Products.products[position].price.toString()
    }

    override fun getItemCount(): Int {

        return Realm.getDefaultInstance().where(ProductRealm::class.java).findAll().size

//        Realm.getDefaultInstance().executeTransaction {
//            realmTransaction ->
//            itemCount = realmTransaction.where(ProductRealm::class.java)
//                .findAll()
//                .size
//
//        }
    }


}