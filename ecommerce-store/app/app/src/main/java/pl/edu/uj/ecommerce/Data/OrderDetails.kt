package pl.edu.uj.ecommerce.Data

import io.realm.RealmObject

open class OrderDetailsRealm : RealmObject() {
    var orderId : Int = -1
    var productId : Int = -1
    var quantity : Int = -1
}

class OrderDetails {

}