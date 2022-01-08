package pl.edu.uj

import pl.edu.uj.models.Cart
import pl.edu.uj.models.Customer
import pl.edu.uj.models.Order
import pl.edu.uj.models.Product
/*
DATA FOR TESTS
 */

// Customer

val customer = Customer("customerTest",
    "jan",
    "kowalski",
    "kowalski@gmail.com",
    "1234")

val customerUpdated = Customer("customerTest",
    "janek",
    "kowalski",
    "kowalskiii@gmail.com",
    "1234")

// Products

val product1 = Product("productTest1",
    100,
    "productTest1",
    1)

val product1Updated = Product("productTestUpdated",
    1000,
    "productTest1",
    1)

val emptyProductList = mutableListOf<Product>()

// Cart

val cart = mutableListOf(Cart(customer.id, product1.id, 1))
val emptyCart = mutableListOf<Cart>()

// Order

val emptyOrderList = mutableListOf<Order>()
