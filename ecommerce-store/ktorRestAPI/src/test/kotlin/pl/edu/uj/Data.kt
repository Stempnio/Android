package pl.edu.uj

import pl.edu.uj.models.*

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

val emptyCustomerList = mutableListOf<Customer>()

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

val cartItem = Cart(customerId = "aaa", productId = 22, quantity = 1)

val cart = mutableListOf(Cart(customer.id, product1.id, 1))
val cart2quantity = mutableListOf(Cart(customer.id, product1.id, 2))
val emptyCart = mutableListOf<Cart>()

// Order

val order = Order(customerId = customer.id, id = 1, date = "")
val emptyOrderList = mutableListOf<Order>()

// Order Details

val orderDetails1 = mutableListOf(OrderDetails(1,1,1))

// Admin

val admin = Admin(id = "adminono", password = "1234", email = "email@gmail.com")
