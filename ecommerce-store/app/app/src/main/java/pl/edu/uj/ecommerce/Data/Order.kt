package pl.edu.uj.ecommerce.Data
import io.realm.RealmObject
import java.time.LocalDateTime

open class OrderRealm : RealmObject() {
    var customerId : String = ""
    //TODO date format
//    var date : LocalDateTime = LocalDateTime.MIN
    var id : Int = -1

}

class Order {

}