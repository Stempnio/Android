package pl.edu.uj.ecommerce

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

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

//        getCustomerByIdIntoDB(CURRENT_CUSTOMER_ID)
//
//        getCartIntoDB(CURRENT_CUSTOMER_ID)
    }


}

