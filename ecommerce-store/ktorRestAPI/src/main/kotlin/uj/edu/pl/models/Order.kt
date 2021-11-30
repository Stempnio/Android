package pl.edu.uj.models
//import kotlinx.serialization.Serializable
//
//@Serializable
data class Order(val id: String, val contents: List<Product>)

val orderStorage = mutableListOf<Order>()