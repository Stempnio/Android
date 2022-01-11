package pl.edu.uj.ecommerce

import pl.edu.uj.ecommerce.Data.*

val testCustomer = Customer().apply {
    this.id="testCustomer"
    this.password = "1234"
}

val testProduct = Product().apply {
    this.id = 1
    this.price = 100
    this.name = "test product 1"
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
    getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)
}

fun addTestProduct() {

    deleteAllProductsFromDB()

    RetrofitService
        .create()
        .deleteAllProductsCall()
        .execute()

    RetrofitService
        .create()
        .postProductCall(testProduct)
        .execute()

    getProductsIntoDB()

}

fun addTestCartItem() {
    RetrofitService
        .create()
        .postCartItemCall(testCustomer.id, testProduct.id)
        .execute()

    getCartIntoDB()

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

fun addTestOrderDetailsToDB() {

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

}



