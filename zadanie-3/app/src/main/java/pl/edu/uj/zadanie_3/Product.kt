package pl.edu.uj.zadanie_3

data class Product(val productName : String, val productPrice : Double)

object Cart {
    var productsInCart = ArrayList<Product>()


    fun addProduct(productName : String, productPrice : Double) {
        val p = Product(productName, productPrice)
        productsInCart.add(p)
    }

    fun removeProduct(productName : String, productPrice: Double) {
        if(productsInCart.contains(Product(productName, productPrice)))
            productsInCart.remove(Product(productName, productPrice))
    }

    fun sampleList() {
        productsInCart.add(Product("product 1", 19900.9))
        productsInCart.add(Product("product 2", 1234.0))
        productsInCart.add(Product("product 3", 10.99))
        productsInCart.add(Product("product 4", 200.0))
        productsInCart.add(Product("product 5", 192.99))
    }
//
//    init {
//        sampleList()
//    }


}