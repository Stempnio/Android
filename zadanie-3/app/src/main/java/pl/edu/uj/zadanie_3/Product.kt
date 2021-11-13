package pl.edu.uj.zadanie_3

data class Product(val productName : String, val productPrice : Double)

object Cart {
    var productsInCart = mutableListOf<Product>()


    fun addProduct(productName : String, productPrice : Double) {
        val p = Product(productName, productPrice)
        productsInCart.add(p)
    }

    fun removeProduct(productName : String, productPrice: Double) {
        if(productsInCart.contains(Product(productName, productPrice)))
            productsInCart.remove(Product(productName, productPrice))
    }


}