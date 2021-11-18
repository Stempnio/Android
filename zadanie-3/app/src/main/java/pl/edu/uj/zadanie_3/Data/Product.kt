package pl.edu.uj.zadanie_3

data class Product(val productName : String, val productPrice : Double)

object Products {
    var products = ArrayList<Product>()

    init {
        sampleList()
    }

    fun sampleList() {
        products.add(Product("product 1", 19900.9))
        products.add(Product("product 2", 1234.0))
        products.add(Product("product 3", 10.99))
        products.add(Product("product 4", 200.0))
        products.add(Product("product 5", 192.99))
        products.add(Product("product 6", 20.0))
        products.add(Product("product 7", 100000.0))
    }

//
//    fun addProduct(productName : String, productPrice : Double) {
//        val p = Product(productName, productPrice)
//        products.add(p)
//    }
//
//    fun removeProduct(productName : String, productPrice: Double) {
//        if(products.contains(Product(productName, productPrice)))
//            products.remove(Product(productName, productPrice))
//    }
}

object Cart {
    var productsInCart = ArrayList<Product>()

    fun totalPrice() : Double {
        var price : Double = 0.0
        for(p : Product in productsInCart) {
            price += p.productPrice
        }

        return price
    }
//
//    fun addProduct(productName : String, productPrice : Double) {
//        val p = Product(productName, productPrice)
//        productsInCart.add(p)
//    }
//
//    fun removeProduct(productName : String, productPrice: Double) {
//        if(productsInCart.contains(Product(productName, productPrice)))
//            productsInCart.remove(Product(productName, productPrice))
//    }


}