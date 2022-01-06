package pl.edu.uj.ecommerce.admin

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

data class Admin(var id : String = "",
                 var email : String = "",
                 var password : String = "")


