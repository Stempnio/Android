package pl.edu.uj.models

data class Customer(val id : String, val firstName : String, val lastName : String, val email : String)

val customerStorage = mutableListOf<Customer>()