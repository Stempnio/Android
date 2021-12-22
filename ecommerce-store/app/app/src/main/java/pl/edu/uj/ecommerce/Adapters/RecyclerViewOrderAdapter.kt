package pl.edu.uj.ecommerce.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import pl.edu.uj.ecommerce.Data.OrderRealm
import pl.edu.uj.ecommerce.R

internal class RecyclerViewOrderAdapter(data: OrderedRealmCollection<OrderRealm?>?) :
    RealmRecyclerViewAdapter<OrderRealm?,
            RecyclerViewOrderAdapter.OrderViewHolder?>(data, true) {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewOrderId : TextView = itemView.findViewById(R.id.textViewOrderId)
        val textViewOrderDate : TextView = itemView.findViewById(R.id.textViewOrderDate)
        val buttonOrderDetails : TextView = itemView.findViewById(R.id.buttonOrderDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_order, parent, false)

        return OrderViewHolder(view)
    }


    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderRealm = getItem(position)
        if (orderRealm != null) {
            val order = Realm.getDefaultInstance()
                .where(OrderRealm::class.java)
                .equalTo("id", orderRealm.id)
                .findFirst()

            val orderIdString = "Order ID: " + order?.id.toString()
            holder.textViewOrderId.text = orderIdString

            holder.textViewOrderDate.text = order?.date.toString().substring(0..9)

            holder.buttonOrderDetails.setOnClickListener {
                Navigation
                    .createNavigateOnClickListener(R.id.action_ordersFragment_to_orderDetailsFragment,
                        bundleOf("orderId" to order?.id.toString())
                    )
                    .onClick(holder.buttonOrderDetails)
            }

        }
    }
    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

}