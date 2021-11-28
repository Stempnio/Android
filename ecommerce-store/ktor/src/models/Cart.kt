package pl.edu.uj.models

data class Cart(val contents : List<Product>) {
    fun totalPrice() : Double {
        var price : Double = 0.0
        contents.forEach{product -> price += product.price}
        return price
    }

}
//
//fun main() {
//
//
//    val cart = Cart(listOf(Item(1, "1", 20.0),
//                            Item(2, "2", 30.0),
//                            Item(3, "3", 40.2)))
//    print(cart.totalPrice())
//}