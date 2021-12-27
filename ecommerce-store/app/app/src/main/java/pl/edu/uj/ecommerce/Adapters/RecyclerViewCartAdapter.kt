import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import pl.edu.uj.ecommerce.Data.CartItemRealm
import pl.edu.uj.ecommerce.Data.removeCartItem
import pl.edu.uj.ecommerce.Data.ProductRealm
import pl.edu.uj.ecommerce.R

internal class RecyclerViewCartAdapter(data: OrderedRealmCollection<CartItemRealm?>?) :
    RealmRecyclerViewAdapter<CartItemRealm?,
            RecyclerViewCartAdapter.CartViewHolder?>(data, true) {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewProductName : TextView = itemView.findViewById(R.id.textViewCartProductName)
        val textViewProductPrice : TextView = itemView.findViewById(R.id.textViewCartProductPrice)
        val buttonRmFromCart : Button = itemView.findViewById(R.id.buttonDeleteFromCart)
        val textViewQuantity : TextView = itemView.findViewById(R.id.textViewCartQuantity)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_cart, parent, false)

        return CartViewHolder(view)
    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItemRealm = getItem(position)
        if (cartItemRealm != null) {
            val product = Realm.getDefaultInstance()
                .where(ProductRealm::class.java)
                .equalTo("id", cartItemRealm.productId)
                .findFirst()

            holder.textViewProductName.text = product?.name

            holder.textViewProductPrice.text = product?.price.toString()

            holder.textViewQuantity.text = cartItemRealm.quantity.toString()

            holder.buttonRmFromCart.setOnClickListener {
                removeCartItem(cartItemRealm.productId)
                notifyItemChanged(position)
            }
        }
    }
    override fun getItemId(index: Int): Long {
        return getItem(index)!!.productId.toLong()
    }

}