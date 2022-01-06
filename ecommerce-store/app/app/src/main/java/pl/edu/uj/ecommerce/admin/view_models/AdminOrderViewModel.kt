package pl.edu.uj.ecommerce.admin.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.edu.uj.ecommerce.Data.Order

class AdminOrderViewModel : ViewModel() {
    val orderListString = MutableLiveData<String>()

}