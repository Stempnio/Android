package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.*
import pl.edu.uj.ecommerce.admin.Admin

val testCustomer = Customer().apply {
    this.id="testCustomer"
    this.password = "1234"
}

var testProduct = Product().apply {
    this.id = 1
    this.price = 100
    this.name = "test product 1"
}

val testAdmin = Admin().apply {
    this.id="testadmin"
    this.email="admintest@a.com"
    this.password="admin"
}


fun addTestCustomer() {
    RetrofitService
        .create()
        .deleteCustomerCall(testCustomer.id)
        .execute()

    RetrofitService
        .create()
        .postCustomerCall(testCustomer)
        .execute()

    CURRENT_CUSTOMER_ID = testCustomer.id
    getCustomerByIdIntoRealm(CURRENT_CUSTOMER_ID)
}

fun getAllCustomers() : List<Customer> {
    val list = RetrofitService
        .create()
        .getAllCustomersCall()
        .execute()
        .body()

    return list ?: emptyList()
}

fun addTestProduct() {

    deleteAllProductsFromRealm()

    RetrofitService
        .create()
        .deleteAllProductsCall()
        .execute()

    RetrofitService
        .create()
        .postProductCall(testProduct)
        .execute()

    val prod = RetrofitService
        .create()
        .getProductsCall()
        .execute()
        .body()

    testProduct.id = prod!![0].id

    getProductsIntoRealm()

}

fun getProducts() : List<Product>? {
    return RetrofitService
        .create()
        .getProductsCall()
        .execute()
        .body()
}

fun addTestCartItem() {
    RetrofitService
        .create()
        .postCartItemCall(testCustomer.id, testProduct.id)
        .execute()

    refreshCart()

}

fun addTestOrder() {

    RetrofitService
        .create()
        .deleteAllOrders()
        .execute()

    RetrofitService
        .create()
        .postCartItemCall(testCustomer.id, testProduct.id)
        .execute()

    RetrofitService
        .create()
        .postCustomerOrderCall(CURRENT_CUSTOMER_ID)
        .execute()


    refreshOrders()
}

fun getTestOrder() : Order {
    val list = RetrofitService
        .create()
        .getCustomerOrdersCall(testCustomer.id)
        .execute()
        .body()

    return if(list != null && list.size == 1) {
        list[0]
    } else {
        Order()
    }
}

fun getTestOrderId() : Int {
    val list = RetrofitService
        .create()
        .getCustomerOrdersCall(testCustomer.id)
        .execute()
        .body()

    return if(list != null && list.size == 1) {
        list[0].id
    } else {
        -1
    }
}

fun getTestOrderToString(list : List<OrderDetails>) : String {

    var result = ""
    list.forEach {
        result += "order ID: " + it.orderId +
                " | product ID: " + it.productId +
                " | quantity: " + it.quantity + "\n"
    }

    return result
}

fun addTestAdmin() {
    RetrofitService
        .create()
        .deleteAdminCall(testAdmin.id)
        .execute()

    RetrofitService
        .create()
        .postAdminCall(testAdmin)
        .execute()

    CURRENT_CUSTOMER_ID = testAdmin.id

}



