package pl.edu.uj.ecommerce

import android.app.Application
import io.realm.Realm
import pl.edu.uj.ecommerce.Data.getCustomerByIdIntoDB

class InitRealm : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        getProductsIntoDB()

        getCustomerByIdIntoDB("cust1")

//        val realmName: String = "Database"
//        val config = RealmConfiguration.Builder().name(realmName).build()
//        val backgroundThreadRealm : Realm = Realm.getInstance(config)
    }
}

