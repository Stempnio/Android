package pl.edu.uj.ecommerce.Data

import io.realm.RealmObject

open class CartRealm : RealmObject() {
    var customerId : String = ""
    var productId : Int = -1
    var quantity : Int = -1
}

class Cart {

}
