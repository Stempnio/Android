package pl.edu.uj.models

data class Product(val id : String, val name : String, val price : Double)

val productStorage = mutableListOf<Product>()