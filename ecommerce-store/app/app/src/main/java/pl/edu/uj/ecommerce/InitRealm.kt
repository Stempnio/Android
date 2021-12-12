package pl.edu.uj.ecommerce

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import pl.edu.uj.ecommerce.Data.getCartIntoDB
import pl.edu.uj.ecommerce.Data.getCustomerByIdIntoDB

class InitRealm : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)

        getProductsIntoDB()

        //TODO customer id read on startup
        val tmpCustomerId = "cust1"

        getCustomerByIdIntoDB(tmpCustomerId)

        getCartIntoDB(tmpCustomerId)

//        val realmName: String = "Database"
//        val config = RealmConfiguration.Builder().name(realmName).build()
//        val backgroundThreadRealm : Realm = Realm.getInstance(config)
    }


}

