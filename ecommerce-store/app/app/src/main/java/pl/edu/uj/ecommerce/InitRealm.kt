package pl.edu.uj.ecommerce

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import pl.edu.uj.ecommerce.Data.getProductsIntoRealm

class InitRealm : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(config)

        getProductsIntoRealm()

    }


}

